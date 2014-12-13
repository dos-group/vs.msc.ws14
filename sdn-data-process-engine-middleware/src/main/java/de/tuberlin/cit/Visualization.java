package de.tuberlin.cit;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import org.opendaylight.controller.sal.core.Edge;
import org.opendaylight.controller.sal.core.Node;
import org.opendaylight.controller.topology.northbound.EdgeProperties;
import org.opendaylight.controller.topology.northbound.Topology;
import org.opendaylight.tools.client.rest.Config;
import org.opendaylight.tools.client.rest.FlowprogrammerHelper;
import org.opendaylight.tools.client.rest.StatisticsHelper;
import org.opendaylight.tools.client.rest.TopologyHelper;

import javax.swing.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

public class Visualization {

    private Config config;
    private FlowprogrammerHelper flowHelper;
    private StatisticsHelper statHelper;
    private TopologyHelper topoHelper;


    public Visualization(String username, String password, String ip) {
        config = new Config();
        config.setUsername(username);
        config.setPassword(password);
        config.setAdminUrl("http://" + ip + ":8080");
        // mandatory - json somehow doesn't work
        config.setMediaType(MediaType.APPLICATION_XML_TYPE);

        flowHelper = new FlowprogrammerHelper();
        flowHelper.setConfig(config);

        statHelper = new StatisticsHelper();
        statHelper.setConfig(config);

        topoHelper = new TopologyHelper();
        topoHelper.setConfig(config);
    }

    public Graph<Node, Edge> getTopologyGraph() {
        Graph<Node, Edge> graph = new SparseMultigraph<>();
        Topology topology = topoHelper.getTopology("default").getEntity();

        List<EdgeProperties> properties = topology.getEdgeProperties();
        for (EdgeProperties p : properties) {
            Edge edge = p.getEdge();
            Node tail = edge.getTailNodeConnector().getNodeConnectorNode();
            Node head = edge.getHeadNodeConnector().getNodeConnectorNode();
            graph.addVertex(tail);
            graph.addVertex(head);
            graph.addEdge(edge, tail, head);
        }

        return graph;
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        String username = "admin";
        String password = "admin";

        Visualization visualization = new Visualization(username, password, ip); //We create our graph in here
        // The Layout<V, E> is parameterized by the vertex and edge types
        Graph<Node, Edge> graph = visualization.getTopologyGraph();
        Layout<Integer, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(300,300)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        BasicVisualizationServer<Integer,String> vv;
        vv = new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
