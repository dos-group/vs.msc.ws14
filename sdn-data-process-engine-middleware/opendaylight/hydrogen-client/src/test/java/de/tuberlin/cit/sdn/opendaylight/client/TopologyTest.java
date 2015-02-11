package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TopologyTest extends BaseTest {
    private TopologyClient topologyClient;

    @Before
    public void init() throws IOException {
        super.init();
        topologyClient = new TopologyClient(settings);
    }

    @Test
    public void getTopology() {
        topologyClient.getTopology();
    }
}
