package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.StaticGraphUtils;
import de.tuberlin.cit.graph.model.NetworkEdge;
import de.tuberlin.cit.graph.model.NetworkVertex;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import org.apache.commons.collections15.Transformer;

import java.util.List;

public class DistanceCalculator {

    private DirectedSparseMultigraph graph;

    public DistanceCalculator(DirectedSparseMultigraph graph) {
        this.graph=graph;
    }

    public long[][] calculateDistanceMatrix(final List<NetworkVertex> vertices) {
        int numberOfVertices = vertices.size();
        long[][] distances = new long[numberOfVertices][numberOfVertices];
        for (int indexVertexFrom = 0; indexVertexFrom < vertices.size(); indexVertexFrom++) {
            for (int indexVertexTo = 0; indexVertexTo < vertices.size(); indexVertexTo++) {
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

    private Number getDistanceOfTheShortestPath(final NetworkVertex node1, final NetworkVertex node2, long sizeOfDataToTransport) {
        Transformer transformer = StaticGraphUtils.createSimpleTransporter();
        DijkstraShortestPath<NetworkVertex, NetworkEdge> alg = new DijkstraShortestPath(this.graph, transformer);
        return alg.getDistance(node1, node2);
    }
}
