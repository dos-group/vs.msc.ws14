package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.Host;
import de.tuberlin.cit.graph.model.NetworkEdge;
import de.tuberlin.cit.graph.model.NetworkVertex;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.util.ArrayList;
import java.util.List;

public class PhysicalNetwork {
    private static PhysicalNetwork instance;

    private DirectedSparseMultigraph graph;
    private HostGroupCollection hostGroups;

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
        for (NetworkEdge edge : edges) {
            vertices.add(edge.getHeadVertex());
            vertices.add(edge.getTailVertex());
        }
        this.hostGroups = new HostGroupCollection(graph);
    }

    /**
     * This method can be used by Flink to get the best next host.
     *
     * @return Id of the next best host or <code>null</code> if no free host.
     */
    public String getNextBestHost() {
        Host nextHost = this.hostGroups.findNextBestHost();
        return nextHost == null ? null : nextHost.getId();
    }

    /**
     * This method should be called when the job is done.
     *
     * @param hostId Id of the Host to unblock
     */
    public void releaseHost(String hostId) {
        this.hostGroups.releaseHost(hostId);
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
