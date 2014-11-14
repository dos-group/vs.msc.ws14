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
    Type: Fat Tree, 4 hosts
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

    info('*** Adding switch\n')
    s1 = net.addSwitch('s1')
    s2 = net.addSwitch('s2')
    s3 = net.addSwitch('s3')

    linkoptsLg = dict(cls=TCLink, bw=20, delay='2ms', loss=1)
    linkoptsSm = dict(cls=TCLink, bw=5, delay='10ms', loss=2)

    info('*** Creating links\n')
    net.addLink(h1, s1, **linkoptsSm)
    net.addLink(h2, s1, **linkoptsSm)
    net.addLink(h3, s2, **linkoptsSm)
    net.addLink(h4, s2, **linkoptsSm)
    net.addLink(s1, s3, **linkoptsLg)
    net.addLink(s2, s3, **linkoptsLg)

    info('*** Starting network\n')
    net.start()

    info('*** Running CLI\n')
    CLI(net)

    info('*** Stopping network')
    net.stop()

if __name__ == '__main__':
    setLogLevel('info')
    customNet()
