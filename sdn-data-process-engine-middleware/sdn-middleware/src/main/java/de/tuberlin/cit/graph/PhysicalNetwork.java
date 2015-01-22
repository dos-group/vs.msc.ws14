package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.NetworkEdge;
import de.tuberlin.cit.graph.model.NetworkVertex;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Transformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhysicalNetwork {
    private static PhysicalNetwork instance;

    private DirectedSparseMultigraph graph;

    private PhysicalNetwork() {
    }

    public static PhysicalNetwork getInstance() {
        if (instance == null) {
            instance = new PhysicalNetwork();
        }
        return instance;
    }

    public void createOrUpdateGraph(final List<NetworkEdge> edges) {
        this.graph = createGraph(edges);
        List<NetworkVertex> vertices = new ArrayList<>();
        for (NetworkEdge edge: edges) {
            vertices.add(edge.getHeadVertex());
            vertices.add(edge.getTailVertex());
        }
    }

    private static DirectedSparseMultigraph<NetworkVertex, NetworkEdge> createGraph(List<NetworkEdge> edges) {
        final DirectedSparseMultigraph<NetworkVertex, NetworkEdge> directedGraph = new DirectedSparseMultigraph();
        for (NetworkEdge edge : edges) {
            directedGraph.addEdge(edge, edge.getTailVertex(), edge.getHeadVertex(), EdgeType.DIRECTED);
        }
        return directedGraph;
    }

    public DirectedSparseMultigraph getGraph() {
        return graph;
    }
}
