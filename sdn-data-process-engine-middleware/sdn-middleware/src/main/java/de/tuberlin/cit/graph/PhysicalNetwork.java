package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.NetworkEdge;
import de.tuberlin.cit.graph.model.NetworkVertex;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Transformer;

import java.util.List;

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
        final DirectedSparseMultigraph<NetworkVertex, NetworkEdge> directedGraph = new DirectedSparseMultigraph();
        for (NetworkEdge edge : edges) {
            directedGraph.addEdge(edge, edge.getTailVertex(), edge.getHeadVertex(), EdgeType.DIRECTED);
        }
        this.graph = directedGraph;
    }

    public Number getLengthIfTheShortestPath(final NetworkVertex node1, final NetworkVertex node2, long sizeOfDataToTransport){
        Transformer transformer = StaticGraphUtils.createTransporter(sizeOfDataToTransport);
        DijkstraShortestPath<NetworkVertex,NetworkEdge> alg = new DijkstraShortestPath(this.graph, transformer);
        return alg.getDistance(node1,node2);
    }

    public DirectedSparseMultigraph getGraph() {
        return graph;
    }
}
