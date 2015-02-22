package de.tuberlin.cit.sdn.opendaylight.hydrogen.client;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FlowProgrammerTest extends BaseTest {
    FlowProgrammerClient flowClient;

    @Before
    public void init() throws IOException {
        super.init();
        flowClient = new FlowProgrammerClient(settings);
    }

    @Test
    public void getFlows() {
        flowClient.getFlows();
    }
}
