package de.tuberlin.cit;

import de.tuberlin.cit.sdn.opendaylight.client.*;
import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.flow.Flows;
import de.tuberlin.cit.sdn.opendaylight.model.host.Hosts;
import de.tuberlin.cit.sdn.opendaylight.model.node.Nodes;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.FlowStatistics;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.PortStatistics;
import de.tuberlin.cit.sdn.opendaylight.model.topology.Topology;

public class App {

    public static void main(String[] args) {

        OdlSettings odlSettings = Utils.getInstance().readSettings(args);

        Utils.getInstance().startRMIServer();

        SwitchManagerClient switchClient = new SwitchManagerClient(odlSettings);
        Nodes nodes = switchClient.getNodes();

        TopologyClient topoClient = new TopologyClient(odlSettings);
        Topology topology = topoClient.getTopology();

        HostTrackerClient hostClient = new HostTrackerClient(odlSettings);
        Hosts activeHosts = hostClient.getActiveHosts();
        Hosts inactiveHosts = hostClient.getInactiveHosts();

        FlowProgrammerClient flowClient = new FlowProgrammerClient(odlSettings);
        Flows flows = flowClient.getFlows();

        StatisticsClient statClient = new StatisticsClient(odlSettings);
        PortStatistics portStatistics = statClient.getPortStatistics();
        FlowStatistics flowStatistics = statClient.getFlowStatistics();
    }
}
