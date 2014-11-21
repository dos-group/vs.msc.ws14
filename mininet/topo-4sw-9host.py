"""Custom topology example

Two directly connected switches plus a host for each switch:

   host --- switch --- switch --- host

Adding the 'topos' dict with a key/value pair to generate our newly defined
topology enables one to pass in '--topo=mytopo' from the command line.
"""

from mininet.topo import Topo

class MyTopo( Topo ):
    "Simple topology example."

    def __init__( self ):
        "Create custom topo."

        # Initialize topology
        Topo.__init__( self )

        # Add hosts and switches
        h11 = self.addHost( 'h11', ip='10.0.0.11')
        h12 = self.addHost( 'h12', ip='10.0.0.12') 
        h13 = self.addHost( 'h13', ip='10.0.0.13')

        h21 = self.addHost( 'h21', ip='10.0.0.21') 
        h22 = self.addHost( 'h22', ip='10.0.0.22')
        h23 = self.addHost( 'h23', ip='10.0.0.23')

        h31 = self.addHost( 'h31', ip='10.0.0.31')
        h32 = self.addHost( 'h32', ip='10.0.0.32')
        h33 = self.addHost( 'h33', ip='10.0.0.33')

        s1 = self.addSwitch( 's1', ip='10.0.0.1')
        s2 = self.addSwitch( 's2', ip='10.0.0.2')
        s3 = self.addSwitch( 's3', ip='10.0.0.3')
        s4 = self.addSwitch( 's4', ip='10.0.0.4')

        # Add links
        self.addLink(h11, s1);
        self.addLink(h12, s1);
        self.addLink(h13, s1);

        self.addLink(h21, s2);
        self.addLink(h22, s2);
        self.addLink(h23, s2);

        self.addLink(h31, s3);
        self.addLink(h32, s3);
        self.addLink(h33, s3);

	self.addLink(s1,s4);
	self.addLink(s2,s4);
	self.addLink(s3,s2);


topos = { 'mytopo': ( lambda: MyTopo() ) }
