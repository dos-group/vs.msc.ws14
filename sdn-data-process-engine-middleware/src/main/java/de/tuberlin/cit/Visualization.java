package de.tuberlin.cit;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;
import org.opendaylight.controller.hosttracker.northbound.HostConfig;
import org.opendaylight.controller.hosttracker.northbound.Hosts;
import org.opendaylight.controller.sal.core.Edge;
import org.opendaylight.controller.topology.northbound.EdgeProperties;
import org.opendaylight.controller.topology.northbound.Topology;
import org.opendaylight.tools.client.rest.*;

import javax.swing.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.Collection;
import java.util.List;

public class Visualization {

    private Config config = new Config();
    private FlowprogrammerHelper flowHelper = new FlowprogrammerHelper();
    private StatisticsHelper statHelper = new StatisticsHelper();
    private TopologyHelper topoHelper = new TopologyHelper();
    private HosttrackerHelper hostHelper = new HosttrackerHelper();


    public Visualization(String username, String password, String ip) {
        config = new Config();
        config.setUsername(username);
        config.setPassword(password);
        config.setAdminUrl("http://" + ip + ":8080");
        // mandatory - json somehow doesn't work
        config.setMediaType(MediaType.APPLICATION_XML_TYPE);

        flowHelper.setConfig(config);
        statHelper.setConfig(config);
        topoHelper.setConfig(config);
        hostHelper.setConfig(config);
    }

    public Graph<String, String> getNetworkGraph() {
        Graph<String, String> graph = new UndirectedSparseGraph<>();

        // in ODL - node == switch
        // switch - switch relationship
        Topology topology = topoHelper.getTopology("default").getEntity();
        List<EdgeProperties> properties = topology.getEdgeProperties();
        if (properties != null) {
            for (EdgeProperties p : properties) {
                Edge edge = p.getEdge();
                String tail = edge.getTailNodeConnector().getNodeConnectorNode().getNodeIDString();
                String head = edge.getHeadNodeConnector().getNodeConnectorNode().getNodeIDString();
                graph.addVertex(head);
                graph.addVertex(tail);
                graph.addEdge(head + tail, head, tail);
            }
        }

        // host - switch relationships
        Hosts hosts = hostHelper.getActiveHosts("default").getEntity();
        Collection<HostConfig> hostConfigs = hosts.getHostConfig();
        if (hostConfigs != null) {
            for (HostConfig hc : hostConfigs) {
                String sw = hc.getNodeId();
                String ip = hc.getNetworkAddress();
                graph.addVertex(sw);
                graph.addVertex(ip);
                graph.addEdge(ip + sw, ip, sw);
            }
        }

        return graph;
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        String username = "admin";
        String password = "admin";

        Visualization visualization = new Visualization(username, password, ip); //We create our graph in here
        Graph<String, String> graph = visualization.getNetworkGraph();

        Layout<String, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(300,300));
        BasicVisualizationServer<String,String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(350,350));

        Transformer<String,Paint> vertexPaint = i -> Color.GREEN;

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);

        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
