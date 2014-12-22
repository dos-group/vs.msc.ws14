package de.tuberlin.cit.sdn.opendaylight.model.statistic;

import de.tuberlin.cit.sdn.opendaylight.model.node.NodeConnector;

public class PortNodeConnectorStatistic {
    
    public NodeConnector nodeConnector;

    public int receivePackets;
    public int transmitPackets;
    public int receiveBytes;
    public int transmitBytes;
    public int receiveDrops;
    public int transmitDrops;
    public int receiveErrors;
    public int transmitErrors;
    public int receiveFrameError;
    public int receiveOverRunError;
    public int receiveCrcError;
    public int collisionCount;
}

