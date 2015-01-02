package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.model.node.Nodes;

public class SwitchManagerClient extends AbstractClient {

    @Override
    public String getBaseUrl() {
        return "/controller/nb/v2/switchmanager/";
    }

    public Nodes getNodes() {
        return getNodes("default");
    }

    public Nodes getNodes(String container) {
        Nodes nodes = resource(container + "/nodes").get(Nodes.class);
        return nodes;
    }
}
