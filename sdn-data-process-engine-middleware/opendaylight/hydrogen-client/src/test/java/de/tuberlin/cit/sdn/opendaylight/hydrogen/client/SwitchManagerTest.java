package de.tuberlin.cit.sdn.opendaylight.hydrogen.client;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SwitchManagerTest extends BaseTest {
    private SwitchManagerClient switchClient;

    @Before
    public void init() throws IOException {
        super.init();
        switchClient = new SwitchManagerClient(settings);
    }

    @Test
    public void getNodes() {
        switchClient.getNodes();
    }

}
