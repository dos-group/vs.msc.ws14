package de.tuberlin.cit;

import de.tuberlin.cit.sdn.opendaylight.client.*;
import de.tuberlin.cit.sdn.opendaylight.model.flow.Flows;
import de.tuberlin.cit.sdn.opendaylight.model.host.Hosts;
import de.tuberlin.cit.sdn.opendaylight.model.node.Nodes;
import de.tuberlin.cit.sdn.opendaylight.model.statistic.PortStatistics;
import de.tuberlin.cit.sdn.opendaylight.model.topology.Topology;

public class App {

    public static void main(String[] args) {
        SwitchManagerClient switchClient = new SwitchManagerClient();
        Nodes nodes = switchClient.getNodes();

        TopologyClient topoClient = new TopologyClient();
        Topology topology = topoClient.getTopology();

        HostTrackerClient hostClient = new HostTrackerClient();
        Hosts activeHosts = hostClient.getActiveHosts();
        Hosts inactiveHosts = hostClient.getInactiveHosts();

        FlowProgrammerClient flowClient = new FlowProgrammerClient();
        Flows flows = flowClient.getFlows();

        StatisticsClient statClient = new StatisticsClient();
        PortStatistics portStatistics = statClient.getPortStatistics();
    }
}
