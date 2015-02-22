package de.tuberlin.cit.sdn.opendaylight.hydrogen.model.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.node.Node;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.node.NodeConnector;

public class HostConfig {
    public String dataLayerAddress;
    public String nodeType;
    public String nodeId;
    public String nodeConnectorType;
    public String nodeConnectorId;
    public String vlan;
    public boolean staticHost;
    public String networkAddress;

    @JsonIgnore
    public Node getNode() {
        Node node = new Node();
        node.id = nodeId;
        node.type = nodeType;
        return node;
    }

    @JsonIgnore
    public NodeConnector getNodeConnector() {
        NodeConnector nodeConnector = new NodeConnector();
        nodeConnector.node = getNode();
        nodeConnector.id = nodeConnectorId;
        nodeConnector.type = nodeConnectorType;
        return nodeConnector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HostConfig that = (HostConfig) o;

        if (!networkAddress.equals(that.networkAddress)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return networkAddress.hashCode();
    }
}
