package de.tuberlin.cit.sdn.middleware;

import de.tuberlin.cit.sdn.middleware.graph.NetworkFactory;
import de.tuberlin.cit.sdn.middleware.graph.PhysicalNetwork;
import de.tuberlin.cit.sdn.middleware.graph.StaticGraphUtils;
import de.tuberlin.cit.sdn.middleware.graph.model.Host;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkVertex;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.helium.client.TopologyClient;
import de.tuberlin.cit.sdn.opendaylight.helium.model.topology.Topology;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Visualization {

    private NetworkFactory networkFactory = new NetworkFactory();
    private PhysicalNetwork network = PhysicalNetwork.getInstance();

    private TopologyClient topologyClient;

    public Visualization(OdlSettings settings) {
        topologyClient = new TopologyClient(settings);
    }

    public static void main(String[] args) throws IOException {
        Visualization visualization = new Visualization(Utils.getInstance().readSettings(args)); //We create our graph in here
        visualization.run();
    }

    private void run() throws IOException {
        List<Topology> topologies = topologyClient.getTopologies();
        if (topologies == null || topologies.isEmpty()) {
            return;
        }
        network.createOrUpdateGraph(networkFactory.createNetworkEdges(topologies.get(0)));

        Graph<NetworkVertex, NetworkEdge> graph = network.getGraph();
        JFrame frame = StaticGraphUtils.visualizeNetworkGraph(graph);
        frame.setVisible(true);
    }
}
