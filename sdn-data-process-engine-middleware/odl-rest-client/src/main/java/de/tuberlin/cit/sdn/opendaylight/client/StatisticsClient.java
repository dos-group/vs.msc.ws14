package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.node.Node;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.FlowNodeStatistic;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.FlowStatistics;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.PortNodeStatistic;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.PortStatistics;

public class StatisticsClient extends AbstractClient {

    public StatisticsClient(OdlSettings settings) {
        super(settings);
    }

    public StatisticsClient() {
        super();
    }

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

    public FlowStatistics getFlowStatistics() {
        return getFlowStatistics("default");
    }

    public FlowStatistics getFlowStatistics(String container) {
        return resource(container + "/flow").get(FlowStatistics.class);
    }

    public FlowNodeStatistic getFlowNodeStatistic(Node node) {
        return getFlowNodeStatistic(node, "default");
    }

    public FlowNodeStatistic getFlowNodeStatistic(Node node, String container) {
        return resource(container + "/flow/node/" + node.type + "/" + node.id).get(FlowNodeStatistic.class);
    }
}
