package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

public class TopologyTest {
    private TopologyClient topologyClient;

    @Before
    public void init() {
        topologyClient = new TopologyClient();
    }

    @Test
    public void getTopology() {
        topologyClient.getTopology();
    }
}
