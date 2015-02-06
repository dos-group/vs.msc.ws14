package de.tuberlin.cit.sdn.middleware.graph;

import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkVertex;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import org.apache.commons.collections15.Transformer;

import java.util.List;

public class DistanceCalculator {

    private DirectedSparseMultigraph graph;

    public DistanceCalculator(DirectedSparseMultigraph graph) {
        this.graph = graph;
    }

    public DistanceMatrix calculateDistanceMatrixX(final List<? extends NetworkVertex> vertices) {
        DistanceMatrix matrix = new DistanceMatrix();
        for (NetworkVertex from : vertices) {
            for (NetworkVertex to : vertices) {
                if (!from.equals(to)) {
                    Number distance = getDistanceOfTheShortestPath(from, to);
                    matrix.putDistance(from.getId(), to.getId(), distance);
                }
            }
        }
        return matrix;
    }

    private Number getDistanceOfTheShortestPath(final NetworkVertex node1, final NetworkVertex node2) {
        Transformer transformer = StaticGraphUtils.createSimpleTransporter();
        DijkstraShortestPath<NetworkVertex, NetworkEdge> alg = new DijkstraShortestPath(this.graph, transformer);
        return alg.getDistance(node1, node2);
    }
}

