package de.tuberlin.cit;

import de.tuberlin.cit.sdn.opendaylight.client.HostTrackerClient;
import de.tuberlin.cit.sdn.opendaylight.client.SwitchManagerClient;
import de.tuberlin.cit.sdn.opendaylight.client.TopologyClient;

public class App {

    public static void main(String[] args) {
        SwitchManagerClient switchClient = new SwitchManagerClient();
        switchClient.getNodes();

        TopologyClient topoClient = new TopologyClient();
        topoClient.getTopology();

        HostTrackerClient hostClient = new HostTrackerClient();
        hostClient.getActiveHosts();
        hostClient.getInactiveHosts();
    }
}
