package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HostTrackerTest extends BaseTest {
    HostTrackerClient hostClient;

    @Before
    public void init() throws IOException {
        super.init();
        hostClient = new HostTrackerClient(settings);
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
