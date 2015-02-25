#!/usr/bin/python


from mininet.cli import CLI
from mininet.log import lg
from mininet.node import Node, RemoteController, Controller, OVSSwitch
from mininet.topolib import Topo, TreeTopo
from mininet.util import waitListening
from mininet.net import Mininet
from mininet.log import setLogLevel, info
from mininet.link import TCLink, Link
import sys

#################################
def assembleCustomFatTree():

    net = Mininet(link=TCLink, controller=lambda name: RemoteController("c0", ip='127.0.0.1'), cleanup=True)

    net.addController('c0')

    info('*** Adding hosts\n')
    h1 = net.addHost('h1', ip='10.0.0.1')
    h2 = net.addHost('h2', ip='10.0.0.9')
    h3 = net.addHost('h3', ip='10.0.0.13')
    h4 = net.addHost('h4', ip='10.0.0.4')
    h5 = net.addHost('h5', ip='10.0.0.11')
    h6 = net.addHost('h6', ip='10.0.0.6')
    h7 = net.addHost('h7', ip='10.0.0.15')
    h8 = net.addHost('h8', ip='10.0.0.5')
    h9 = net.addHost('h9', ip='10.0.0.2')
    h10 = net.addHost('h10', ip='10.0.0.10')
    h11 = net.addHost('h11', ip='10.0.0.8')
    h12 = net.addHost('h12', ip='10.0.0.12')
    h13 = net.addHost('h13', ip='10.0.0.3')
    h14 = net.addHost('h14', ip='10.0.0.16')
    h15 = net.addHost('h15', ip='10.0.0.7')
    h16 = net.addHost('h16', ip='10.0.0.14')

    info('*** Adding switches\n')
    s1 = net.addSwitch('s1')
    s2 = net.addSwitch('s2')
    s3 = net.addSwitch('s3')
    s4 = net.addSwitch('s4')
    s5 = net.addSwitch('s5')
    s6 = net.addSwitch('s6')
    s7 = net.addSwitch('s7')

    # var delay for easy testing
    dly = "2ms"

    # lame links so we see better effects w/ our setup
    linkoptsSmall = dict(cls=TCLink, bw=5, delay=dly, loss=0)
    linkoptsMedium = dict(cls=TCLink, bw=10, delay=dly, loss=0)
    linkoptsLarge = dict(cls=TCLink, bw=20, delay=dly, loss=0)

    info('*** Creating links\n')
    net.addLink(h1, s4, **linkoptsSmall)
    net.addLink(h2, s4, **linkoptsSmall)
    net.addLink(h3, s4, **linkoptsSmall)
    net.addLink(h4, s5, **linkoptsSmall)
    net.addLink(h5, s5, **linkoptsSmall)
    net.addLink(h6, s5, **linkoptsSmall)
    net.addLink(h7, s6, **linkoptsSmall)
    net.addLink(h8, s6, **linkoptsSmall)
    net.addLink(h9, s6, **linkoptsSmall)
    net.addLink(h10, s7, **linkoptsSmall)
    net.addLink(h11, s7, **linkoptsSmall)
    net.addLink(h12, s7, **linkoptsSmall)
    net.addLink(h13, s7, **linkoptsSmall)
    net.addLink(h14, s7, **linkoptsSmall)
    net.addLink(h15, s7, **linkoptsSmall)
    net.addLink(h16, s7, **linkoptsSmall)
    net.addLink(s4, s2, **linkoptsMedium)
    net.addLink(s5, s2, **linkoptsMedium)
    net.addLink(s3, s6, **linkoptsMedium)
    net.addLink(s3, s7, **linkoptsMedium)
    net.addLink(s1, s2, **linkoptsLarge)
    net.addLink(s1, s3, **linkoptsLarge)

    return net


def startNAT( root, inetIntf='eth0', subnet='10.0/8' ):
    """Start NAT/forwarding between Mininet and external network
    root: node to access iptables from
    inetIntf: interface for internet access
    subnet: Mininet subnet (default 10.0/8)="""

    # Identify the interface connecting to the mininet network
    localIntf = root.defaultIntf()

    # Flush any currently active rules
    root.cmd( 'iptables -F' )
    root.cmd( 'iptables -t nat -F' )

    # Create default entries for unmatched traffic
    root.cmd( 'iptables -P INPUT ACCEPT' )
    root.cmd( 'iptables -P OUTPUT ACCEPT' )
    root.cmd( 'iptables -P FORWARD DROP' )

    # Configure NAT
    root.cmd( 'iptables -I FORWARD -i', localIntf, '-d', subnet, '-j DROP' )
    root.cmd( 'iptables -A FORWARD -i', localIntf, '-s', subnet, '-j ACCEPT' )
    root.cmd( 'iptables -A FORWARD -i', inetIntf, '-d', subnet, '-j ACCEPT' )
    root.cmd( 'iptables -t nat -A POSTROUTING -o ', inetIntf, '-j MASQUERADE' )

    # Instruct the kernel to perform forwarding
    root.cmd( 'sysctl net.ipv4.ip_forward=1' )

def stopNAT( root ):
    """Stop NAT/forwarding between Mininet and external network"""
    # Flush any currently active rules
    root.cmd( 'iptables -F' )
    root.cmd( 'iptables -t nat -F' )

    # Instruct the kernel to stop forwarding
    root.cmd('sysctl net.ipv4.ip_forward=0')

def fixNetworkManager( root, intf ):
    """Prevent network-manager from messing with our interface,
       by specifying manual configuration in /etc/network/interfaces
       root: a node in the root namespace (for running commands)
       intf: interface name"""
    cfile = '/etc/network/interfaces'
    line = '\niface %s inet manual\n' % intf
    config = open(cfile).read()
    if line not in config:
        print '*** Adding', line.strip(), 'to', cfile
        with open(cfile, 'a') as f:
            f.write(line)
        # Probably need to restart network-manager to be safe -
        # hopefully this won't disconnect you
        root.cmd('service network-manager restart')

def connectToInternet( network, switch='s1', rootip='10.254', subnet='10.0/8'):
    """Connect the network to the internet
       switch: switch to connect to root namespace
       rootip: address for interface in root namespace
       subnet: Mininet subnet"""
    switch = network.get(switch)
    prefixLen = subnet.split('/')[1]

    # Create a node in root namespace
    root = Node('root', inNamespace=False)

    # Prevent network-manager from interfering with our interface
    fixNetworkManager(root, 'root-eth0')

    # Create link between root NS and switch
    linkoptsInet = dict(cls=TCLink, bw=100, delay='2ms', loss=0)
    link = network.addLink(root, switch, **linkoptsInet)
    link.intf1.setIP(rootip, prefixLen)

    # Start network that now includes link to root namespace
    network.start()

    # Start NAT and establish forwarding
    startNAT(root)

    # Establish routes from end hosts
    for host in network.hosts:
        host.cmd('ip route flush root 0/0')
        host.cmd('route add -net', subnet, 'dev', host.defaultIntf())
        host.cmd('route add default gw', rootip)

    # show NAT node in ODL topology
    host1 = network.get('h1')
    host.cmd('ping -c 1 8.8.8.8')

    return root

def sshd(network, cmd='/usr/sbin/sshd', opts='-D'):
    for host in network.hosts:
        host.cmd( cmd + ' ' + opts + '&' )
    print
    print "*** Waiting for ssh daemons to start"
    print

    for server in network.hosts:
        waitListening( server=server, port=22, timeout=5 )

    print
    print "*** Hosts are running sshd at the following addresses:"
    print
    for host in network.hosts:
        print host.name, host.IP()
    print


if __name__ == '__main__':
    cmd='/usr/sbin/sshd'
    lg.setLogLevel('info')

    print
    print "***creating net***"
    print
    net = assembleCustomFatTree()

    rootnode = connectToInternet(net)

    print
    print "***Pingtest after adding NAT***"
    print
    net.pingAll(timeout=1)

    print
    print "***launching sshd***"
    print
    argvopts = ' '.join(sys.argv[1:]) if len(sys.argv) > 1 else ('-D -o UseDNS=no -u0')
    sshd(net, opts=argvopts)

    print
    print "***network running***"
    print
    CLI( net )

    print
    print "***Killing sshd***"
    print
    for host in net.hosts:
        host.cmd( 'kill %' + cmd )

    stopNAT(rootnode)

    net.stop()
    print
    print "***Network stopped***"
    print