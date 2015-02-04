package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

public class HostTrackerTest {
    HostTrackerClient hostClient;

    @Before
    public void init() {
        hostClient = new HostTrackerClient();
    }

    @Test
    public void getActiveHosts() {
        hostClient.getActiveHosts();
    }

    @Test
    public void getInactiveHosts() {
        hostClient.getInactiveHosts();
    }
}
