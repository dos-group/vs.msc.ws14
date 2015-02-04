package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

public class FlowProgrammerTest {
    FlowProgrammerClient flowClient;

    @Before
    public void init() {
        flowClient = new FlowProgrammerClient();
    }

    @Test
    private void getFlows() {
        flowClient.getFlows();
    }
}
