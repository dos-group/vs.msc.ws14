package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

public class SwitchManagerTest {
    private SwitchManagerClient switchClient;

    @Before
    public void init() {
        switchClient = new SwitchManagerClient();
    }

    @Test
    public void getNodes() {
        switchClient.getNodes();
    }

}
