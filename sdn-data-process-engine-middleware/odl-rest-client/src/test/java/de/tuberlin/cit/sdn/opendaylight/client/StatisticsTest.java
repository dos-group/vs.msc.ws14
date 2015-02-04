package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class StatisticsTest extends BaseTest {
    StatisticsClient statisticsClient;

    @Before
    public void init() throws IOException {
        super.init();
        statisticsClient = new StatisticsClient(settings);
    }

    @Test
    public void getFlowStatistics() {
        statisticsClient.getFlowStatistics();
    }

    @Test
    public void getPortStatistics() {
        statisticsClient.getPortStatistics();
    }
}
