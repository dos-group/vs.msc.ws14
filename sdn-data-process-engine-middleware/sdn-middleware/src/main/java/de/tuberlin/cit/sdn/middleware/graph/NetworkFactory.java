package de.tuberlin.cit.sdn.middleware.graph;

import de.tuberlin.cit.sdn.middleware.graph.model.Host;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkDevice;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkVertex;
import de.tuberlin.cit.sdn.opendaylight.helium.model.topology.Link;
import de.tuberlin.cit.sdn.opendaylight.helium.model.topology.Node;
import de.tuberlin.cit.sdn.opendaylight.helium.model.topology.Topology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkFactory {

    public List<NetworkEdge> createNetworkEdges(Topology topology) {
        Map<String, NetworkVertex> nodeMap = new HashMap<>();
        List<NetworkEdge> networkEdges = new ArrayList<>();

        List<Node> nodes = topology.nodes;
        List<Link> links = topology.links;

        if (nodes == null || links == null) {
            return networkEdges;
        }

        // fetch nodes (hosts and switches)
        for (Node n : nodes) {
            if (n.isHost()) {
                Node.Address address = n.getAddress();
                Host host = new Host(address.ip);
                host.setMacAddress(address.mac);
                nodeMap.put(n.id, host);
            } else {
                NetworkDevice device = new NetworkDevice(n.id);
                nodeMap.put(n.id, device);
            }
        }

        // fetch edges
        for (Link l : links) {
            NetworkEdge edge = new NetworkEdge();
            edge.setHead(nodeMap.get(l.dest.node), l.dest.parsePort());
            edge.setTail(nodeMap.get(l.src.node), l.src.parsePort());
            networkEdges.add(edge);
        }

        return networkEdges;
    }
}
