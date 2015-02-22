package de.tuberlin.cit.sdn.middleware;

import de.tuberlin.cit.sdn.middleware.graph.NetworkFactory;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkVertex;
import de.tuberlin.cit.sdn.middleware.graph.PhysicalNetwork;
import de.tuberlin.cit.sdn.opendaylight.client.HostTrackerClient;
import de.tuberlin.cit.sdn.opendaylight.client.SwitchManagerClient;
import de.tuberlin.cit.sdn.opendaylight.client.TopologyClient;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.host.HostConfig;
import de.tuberlin.cit.sdn.opendaylight.model.topology.EdgeProperty;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Visualization {

    private SwitchManagerClient switchClient;
    private TopologyClient topologyClient;
    private HostTrackerClient hostClient;

    private NetworkFactory networkFactory = new NetworkFactory();
    private PhysicalNetwork network = PhysicalNetwork.getInstance();

    public Visualization(OdlSettings odlSettings) {
        switchClient = new SwitchManagerClient(odlSettings);
        topologyClient = new TopologyClient(odlSettings);
        hostClient = new HostTrackerClient(odlSettings);
    }

    public Visualization() {
        switchClient = new SwitchManagerClient();
        topologyClient = new TopologyClient();
        hostClient = new HostTrackerClient();
    }

    public static void main(String[] args) {
        Visualization visualization = new Visualization(Utils.getInstance().readSettings(args)); //We create our graph in here
        visualization.run();
    }

    private void run() {
        List<EdgeProperty> edgeProperties = topologyClient.getTopology().edgeProperties;
        List<HostConfig> hostConfigs = hostClient.getActiveHosts().hostConfig;
        network.createOrUpdateGraph(networkFactory.createNetworkEdges(edgeProperties, hostConfigs));

        Graph<NetworkVertex, NetworkEdge> graph = network.getGraph();

        Layout<NetworkVertex, NetworkEdge> layout = new ISOMLayout<>(graph);

        layout.setSize(new Dimension(600, 600));
        BasicVisualizationServer<NetworkVertex, NetworkEdge> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(700, 700));

        Transformer<NetworkVertex, Paint> vertexPaint = new Transformer<NetworkVertex, Paint>() {
            @Override
            public Paint transform(NetworkVertex v) {
                return Color.GREEN;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        Transformer<NetworkVertex, String> vertexLabel = new Transformer<NetworkVertex, String>() {
            @Override
            public String transform(NetworkVertex v) {
                return v.getId();
            }
        };
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);

        Transformer<NetworkEdge, String> edgeLabel = new Transformer<NetworkEdge, String>() {
            @Override
            public String transform(NetworkEdge networkEdge) {
                return networkEdge.getTailPort() + "->" + networkEdge.getHeadPort();
            }
        };
        vv.getRenderContext().setEdgeLabelTransformer(edgeLabel);

        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
