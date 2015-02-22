package de.tuberlin.cit.sdn.opendaylight.hydrogen.client;

import de.tuberlin.cit.sdn.opendaylight.commons.AbstractClient;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.node.Nodes;

public class SwitchManagerClient extends AbstractClient {

    public SwitchManagerClient(OdlSettings settings) {
        super(settings);
    }

    public SwitchManagerClient() {
        super();
    }

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
