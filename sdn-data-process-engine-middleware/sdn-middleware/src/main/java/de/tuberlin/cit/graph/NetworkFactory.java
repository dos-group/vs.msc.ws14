package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.Host;
import de.tuberlin.cit.graph.model.NetworkDevice;
import de.tuberlin.cit.graph.model.NetworkEdge;
import de.tuberlin.cit.sdn.opendaylight.model.host.HostConfig;
import de.tuberlin.cit.sdn.opendaylight.model.topology.EdgeProperty;

import java.util.ArrayList;
import java.util.List;

public class NetworkFactory {

    public NetworkEdge createNetworkEdge(EdgeProperty e) {
        NetworkEdge networkEdge = new NetworkEdge();
        networkEdge.setTailVertex(new NetworkDevice(e.edge.tailNodeConnector.node.id));
        networkEdge.setTailPort(Integer.parseInt(e.edge.tailNodeConnector.id));
        networkEdge.setHeadVertex(new NetworkDevice(e.edge.headNodeConnector.node.id));
        networkEdge.setHeadPort(Integer.parseInt(e.edge.headNodeConnector.id));
        networkEdge.setBandwidth(e.getBandwidth());
        return networkEdge;
    }

    public NetworkEdge createNetworkEdge(HostConfig hostConfig, boolean isHostHead) {
        NetworkEdge networkEdge = new NetworkEdge();
        if (isHostHead) {
            networkEdge.setTailVertex(new NetworkDevice(hostConfig.nodeId));
            networkEdge.setTailPort(Integer.parseInt(hostConfig.nodeConnectorId));

            Host host = new Host(hostConfig.networkAddress);
            host.setMacAddress(hostConfig.dataLayerAddress);
            networkEdge.setHeadVertex(host);
            networkEdge.setHeadPort(0);
        } else {
            Host host = new Host(hostConfig.networkAddress);
            host.setMacAddress(hostConfig.dataLayerAddress);
            networkEdge.setTailVertex(host);
            networkEdge.setTailPort(0);

            networkEdge.setHeadVertex(new NetworkDevice(hostConfig.nodeId));
            networkEdge.setHeadPort(Integer.parseInt(hostConfig.nodeConnectorId));
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
