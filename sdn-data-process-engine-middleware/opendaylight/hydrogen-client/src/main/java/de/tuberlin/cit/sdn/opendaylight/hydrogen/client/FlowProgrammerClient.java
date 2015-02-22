package de.tuberlin.cit.sdn.opendaylight.hydrogen.client;

import com.sun.jersey.api.client.WebResource;
import de.tuberlin.cit.sdn.opendaylight.commons.AbstractClient;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.flow.FlowConfig;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.flow.Flows;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.node.Node;

public class FlowProgrammerClient extends AbstractClient {

    public FlowProgrammerClient(OdlSettings settings) {
        super(settings);
    }

    public FlowProgrammerClient() {
        super();
    }

    @Override
    public String getBaseUrl() {
        return "/controller/nb/v2/flowprogrammer/";
    }

    public Flows getFlows() {
        return getFlows("default");
    }

    public Flows getFlows(String container) {
        Flows flows = resource(container).get(Flows.class);
        return flows;
    }

    public void putFlowConfig(FlowConfig flowConfig) {
        putFlowConfig(flowConfig, "default");
    }

    public void putFlowConfig(FlowConfig flowConfig, String container) {
        flowConfigResource(flowConfig, container).put(flowConfig);
    }

    public void deleteFlowConfig(FlowConfig flowConfig) {
        deleteFlowConfig(flowConfig, "default");
    }

    public void deleteFlowConfig(FlowConfig flowConfig, String container) {
        flowConfigResource(flowConfig, container).delete();
    }

    private WebResource.Builder flowConfigResource(FlowConfig flowConfig, String container) {
        Node node = flowConfig.node;
        return resource(container + "/node/" + node.type + "/" + node.id + "/staticFlow/" + flowConfig.name);
    }
}
