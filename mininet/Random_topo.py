#!/usr/bin/python

__author__ = "Nico Scherer"

from mininet.net import Mininet
from mininet.node import Controller, RemoteController
from mininet.cli import CLI
from mininet.log import setLogLevel, info
from mininet.link import TCLink


def customNet():

    """
    Creates a custom network.
    "Random" ropology
    """

    """ Insert your controller IP here """
    controllerSettings = dict(controller=RemoteController, ip="192.168.1.74")

    net = Mininet(link=TCLink)

    info('*** Adding controller\n')
    net.addController('c0', **controllerSettings)

    info('*** Adding hosts\n')
    h1 = net.addHost('h1', ip='10.0.0.1')
    h2 = net.addHost('h2', ip='10.0.0.2')
    h3 = net.addHost('h3', ip='10.0.0.3')
    h4 = net.addHost('h4', ip='10.0.0.4')
    h5 = net.addHost('h5', ip='10.0.0.5')
    h6 = net.addHost('h6', ip='10.0.0.6')
    h7 = net.addHost('h7', ip='10.0.0.7')
    h8 = net.addHost('h8', ip='10.0.0.8')
    h9 = net.addHost('h9', ip='10.0.0.9')
    h10 = net.addHost('h10', ip='10.0.0.10')
    h11 = net.addHost('h10', ip='10.0.0.11')
    h12 = net.addHost('h10', ip='10.0.0.12')
    h13 = net.addHost('h10', ip='10.0.0.13')
    h14 = net.addHost('h10', ip='10.0.0.14')
    h15 = net.addHost('h10', ip='10.0.0.15')
    h16 = net.addHost('h10', ip='10.0.0.16')
    h17 = net.addHost('h10', ip='10.0.0.17')
    h18 = net.addHost('h10', ip='10.0.0.18')
    h19 = net.addHost('h10', ip='10.0.0.19')

    info('*** Adding switches\n')
    s1 = net.addSwitch('s1')
    s2 = net.addSwitch('s2')
    s3 = net.addSwitch('s3')
    s4 = net.addSwitch('s4')
    s5 = net.addSwitch('s5')
    s6 = net.addSwitch('s6')
    s7 = net.addSwitch('s7')
    s8 = net.addSwitch('s8')

    linkopts = dict(cls=TCLink, bw=10, delay='10ms', loss=0)

    info('*** Creating links\n')
    net.addLink(h16, s1, **linkopts)
    net.addLink(h17, s1, **linkopts)
    net.addLink(h18, s1, **linkopts)
    net.addLink(h19, s1, **linkopts)
    net.addLink(h13, s2, **linkopts)
    net.addLink(h14, s2, **linkopts)
    net.addLink(h15, s2, **linkopts)
    net.addLink(h12, s3, **linkopts)
    net.addLink(h11, s3, **linkopts)
    net.addLink(h10, s3, **linkopts)
    net.addLink(h9, s3, **linkopts)
    net.addLink(h8, s4, **linkopts)
    net.addLink(h7, s4, **linkopts)
    net.addLink(h6, s4, **linkopts)
    net.addLink(h5, s6, **linkopts)
    net.addLink(h4, s7, **linkopts)
    net.addLink(h1, s8, **linkopts)
    net.addLink(h2, s8, **linkopts)
    net.addLink(h3, s8, **linkopts)

    net.addLink(s8, s1, **linkopts)
    net.addLink(s2, s1, **linkopts)
    net.addLink(s4, s2, **linkopts)
    net.addLink(s4, s3, **linkopts)
    net.addLink(s8, s4, **linkopts)
    net.addLink(s5, s8, **linkopts)
    net.addLink(s5, s4, **linkopts)
    net.addLink(s5, s6, **linkopts)
    net.addLink(s5, s7, **linkopts)

    info('*** Starting network\n')
    net.start()

    info('*** Running CLI\n')
    CLI(net)

    info('*** Stopping network')
    net.stop()

if __name__ == '__main__':
    setLogLevel('info')
    customNet()
