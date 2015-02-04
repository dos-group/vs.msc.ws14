package de.tuberlin.cit.sdn.opendaylight.client;

import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {
    StatisticsClient statisticsClient;

    @Before
    private void init() {
        statisticsClient = new StatisticsClient();
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
