package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.model.node.Node;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.PortNodeStatistic;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.PortStatistics;

public class StatisticsClient extends AbstractClient {

    @Override
    public String getBaseUrl() {
        return "/controller/nb/v2/statistics/";
    }

    public PortStatistics getPortStatistics() {
        return getPortStatistics("default");
    }

    public PortStatistics getPortStatistics(String container) {
        return resource(container + "/port").get(PortStatistics.class);
    }

    public PortNodeStatistic getPortNodeStatistic(Node node) {
        return getPortNodeStatistic(node, "default");
    }

    public PortNodeStatistic getPortNodeStatistic(Node node, String container) {
        return resource(container + "/port/node/" + node.type + "/" + node.id).get(PortNodeStatistic.class);
    }
}
