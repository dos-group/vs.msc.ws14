package de.tuberlin.cit.sdn.middleware.graph;


import de.tuberlin.cit.sdn.middleware.graph.model.Host;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkVertex;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;

public class StaticGraphUtils {

    public static Transformer<NetworkEdge, Long> createTransporter(final long sizeOfDataToTransport) {
        return new Transformer<NetworkEdge, Long>() {
            public Long transform(NetworkEdge link) {
                return link.calculateWeight(sizeOfDataToTransport);
            }
        };
    }

    public static Transformer<NetworkEdge, Long> createSimpleTransporter() {
        return new Transformer<NetworkEdge, Long>() {
            public Long transform(NetworkEdge link) {
                return link.calculateWeight();
            }
        };
    }

    public static JFrame visualizeNetworkGraph(Graph<NetworkVertex, NetworkEdge> graph) {
        Layout<NetworkVertex, NetworkEdge> layout = new ISOMLayout<>(graph);

        layout.setSize(new Dimension(600, 600));
        BasicVisualizationServer<NetworkVertex, NetworkEdge> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(700, 700));

        Transformer<NetworkVertex, Paint> vertexPaint = v -> {
            if (v instanceof Host) {
                Host host = (Host) v;
                return host.isFree() ? Color.GREEN : Color.RED;
            } else {
                return Color.GRAY;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        Transformer<NetworkVertex, String> vertexLabel = v -> v.getId();
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.AUTO);

        Transformer<NetworkEdge, String> edgeLabel = networkEdge -> networkEdge.getTailPort() + "->" + networkEdge.getHeadPort();
        vv.getRenderContext().setEdgeLabelTransformer(edgeLabel);

        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        return frame;
    }
}
