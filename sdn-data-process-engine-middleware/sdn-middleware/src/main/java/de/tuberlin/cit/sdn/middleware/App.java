package de.tuberlin.cit.sdn.middleware;

import de.tuberlin.cit.sdn.middleware.flinkinterface.RestServer;
import de.tuberlin.cit.sdn.middleware.flinkinterface.services.SdnServices;
import de.tuberlin.cit.sdn.middleware.graph.NetworkFactory;
import de.tuberlin.cit.sdn.middleware.graph.PhysicalNetwork;
import de.tuberlin.cit.sdn.middleware.graph.StaticGraphUtils;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkVertex;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.helium.client.TopologyClient;
import de.tuberlin.cit.sdn.opendaylight.helium.model.topology.Topology;
import edu.uci.ics.jung.graph.Graph;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {

        // fetch opendaylight stuff
        OdlSettings settings = Utils.getInstance().readSettings(args);
        TopologyClient topologyClient = new TopologyClient(settings);
        topologyClient.getTopologies();

        List<Topology> topologies = topologyClient.getTopologies();
        if (topologies == null || topologies.isEmpty()) {
            return;
        }

        // create network graph
        PhysicalNetwork network = PhysicalNetwork.getInstance();
        NetworkFactory networkFactory = new NetworkFactory();
        network.createOrUpdateGraph(networkFactory.createNetworkEdges(topologies.get(0)));

        Graph<NetworkVertex, NetworkEdge> graph = network.getGraph();
        JFrame frame = StaticGraphUtils.visualizeNetworkGraph(graph);
        frame.setVisible(true);

        // instantiate rest server
        RestServer rs = new RestServer(new SdnServices() {
            @Override
            public String getExecutionHost() {
                String ip = network.getNextBestHost();
                SwingUtilities.updateComponentTreeUI(frame);
                return ip;
            }

            @Override
            public void markInstanceAsUnused(String ip) {
                network.releaseHost(ip);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        rs.startServer();
    }
}
