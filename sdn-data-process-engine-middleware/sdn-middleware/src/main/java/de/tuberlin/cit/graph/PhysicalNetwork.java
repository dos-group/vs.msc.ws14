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
    private long[][] distances;

    private DirectedSparseMultigraph graph;

    private PhysicalNetwork() {
    }

    public static PhysicalNetwork getInstance() {
        if (instance == null) {
            instance = new PhysicalNetwork();
        }
        return instance;
    }

    public void createOrUpdateGraph(final List<NetworkEdge> edges, final List<NetworkVertex> vertices) {
        this.graph = createGraph(edges);
        this.distances = calculateDistanceMatrix(edges, vertices);
    }

    private long[][] calculateDistanceMatrix(final List<NetworkEdge> edges, final List<NetworkVertex> vertices){
        int numberOfVertices = vertices.size();
        long[][] distances = new long[numberOfVertices][numberOfVertices];
        for (int indexVertexFrom = 0; indexVertexFrom<vertices.size(); indexVertexFrom++){
            for (int indexVertexTo = 0; indexVertexTo<vertices.size(); indexVertexTo++) {
                if (vertices.get(indexVertexFrom).equals(vertices.get(indexVertexTo))) {
                    distances[indexVertexFrom][indexVertexTo] = 0;
                } else {
                    Number distance = getDistanceOfTheShortestPath(vertices.get(indexVertexFrom), vertices.get(indexVertexTo), 1);
                    distances[indexVertexFrom][indexVertexTo] = distance.longValue();
                }
            }
        }
        return distances;
    }

    private DirectedSparseMultigraph<NetworkVertex, NetworkEdge> createGraph(List<NetworkEdge> edges){
        final DirectedSparseMultigraph<NetworkVertex, NetworkEdge> directedGraph = new DirectedSparseMultigraph();
        for (NetworkEdge edge : edges) {
            directedGraph.addEdge(edge, edge.getTailVertex(), edge.getHeadVertex(), EdgeType.DIRECTED);
        }
        return directedGraph;
    }


    private Number getDistanceOfTheShortestPath(final NetworkVertex node1, final NetworkVertex node2, long sizeOfDataToTransport){
        Transformer transformer = StaticGraphUtils.createSimpleTransporter();
        DijkstraShortestPath<NetworkVertex,NetworkEdge> alg = new DijkstraShortestPath(this.graph, transformer);
        return alg.getDistance(node1,node2);
    }

    public DirectedSparseMultigraph getGraph() {
        return graph;
    }
}
