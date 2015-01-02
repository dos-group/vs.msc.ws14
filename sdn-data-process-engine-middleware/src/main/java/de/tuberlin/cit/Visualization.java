package de.tuberlin.cit;

import de.tuberlin.cit.sdn.Utils;
import de.tuberlin.cit.sdn.opendaylight.client.HostTrackerClient;
import de.tuberlin.cit.sdn.opendaylight.client.SwitchManagerClient;
import de.tuberlin.cit.sdn.opendaylight.client.TopologyClient;
import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.host.HostConfig;
import de.tuberlin.cit.sdn.opendaylight.model.node.NodeConnector;
import de.tuberlin.cit.sdn.opendaylight.model.node.NodeProperty;
import de.tuberlin.cit.sdn.opendaylight.model.topology.EdgeProperty;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Visualization {

    private SwitchManagerClient switchClient;
    private TopologyClient topologyClient;
    private HostTrackerClient hostClient;

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

    public Graph<String, String> getNetworkGraph() {
        Graph<String, String> graph = new UndirectedSparseGraph<>();

        List<NodeProperty> nodes = switchClient.getNodes().nodeProperties;
        if (nodes != null) {
            for (NodeProperty n : nodes) {
                graph.addVertex(n.node.id);
            }
        }

        List<HostConfig> hosts = hostClient.getActiveHosts().hostConfig;
        if (hosts != null) {
            for (HostConfig h : hosts) {
                graph.addVertex(h.networkAddress);
                graph.addEdge(h.networkAddress + h.nodeId, h.networkAddress, h.nodeId);
            }
        }

        List<EdgeProperty> edges = topologyClient.getTopology().edgeProperties;
        if (edges != null) {
            for (EdgeProperty e : edges) {
                NodeConnector tail = e.edge.tailNodeConnector;
                NodeConnector head = e.edge.headNodeConnector;
                graph.addEdge(tail.node.id + head.node.id, tail.node.id, head.node.id);
            }
        }

        return graph;
    }

    public static void main(String[] args) {

        Visualization visualization = new Visualization(Utils.getInstance().readSettings(args)); //We create our graph in here
        Graph<String, String> graph = visualization.getNetworkGraph();

        Layout<String, String> layout = new ISOMLayout<>(graph);

        layout.setSize(new Dimension(600, 600));
        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(700, 700));

        Transformer<String, Paint> vertexPaint = i -> Color.GREEN;

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.N);

        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
