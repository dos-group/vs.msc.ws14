package de.tuberlin.cit.sdn.middleware.graph;

import de.tuberlin.cit.sdn.middleware.graph.model.Host;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkDevice;
import de.tuberlin.cit.sdn.middleware.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.opendaylight.model.host.HostConfig;
import de.tuberlin.cit.sdn.opendaylight.model.topology.EdgeProperty;

import java.util.ArrayList;
import java.util.List;

public class NetworkFactory {

    public NetworkEdge createNetworkEdge(EdgeProperty e) {
        NetworkEdge networkEdge = new NetworkEdge();
        networkEdge.setTail(new NetworkDevice(e.edge.tailNodeConnector.node.id), e.edge.tailNodeConnector.id);
        networkEdge.setHead(new NetworkDevice(e.edge.headNodeConnector.node.id), e.edge.headNodeConnector.id);
        networkEdge.setBandwidth(e.getBandwidth());
        return networkEdge;
    }

    public NetworkEdge createNetworkEdge(HostConfig hostConfig, boolean isHostHead) {
        NetworkEdge networkEdge = new NetworkEdge();
        if (isHostHead) {
            networkEdge.setTail(new NetworkDevice(hostConfig.nodeId), hostConfig.nodeConnectorId);

            Host host = new Host(hostConfig.networkAddress);
            host.setMacAddress(hostConfig.dataLayerAddress);
            networkEdge.setHead(host, 0);
        } else {
            Host host = new Host(hostConfig.networkAddress);
            host.setMacAddress(hostConfig.dataLayerAddress);
            networkEdge.setTail(host, 0);

            networkEdge.setHead(new NetworkDevice(hostConfig.nodeId), hostConfig.nodeConnectorId);
        }
        return networkEdge;
    }

    public List<NetworkEdge> createNetworkEdges(List<EdgeProperty> edgeProperties, List<HostConfig> hostConfigs) {
        List<NetworkEdge> list = new ArrayList<>();
        if (edgeProperties != null) {
            for (EdgeProperty e : edgeProperties) {
                list.add(createNetworkEdge(e));
            }
        }
        if (hostConfigs != null) {
            for (HostConfig h : hostConfigs) {
                list.add(createNetworkEdge(h, true));
                list.add(createNetworkEdge(h, false));
            }
        }
        return list;
    }
}
