package de.tuberlin.cit.sdn.opendaylight.client;

import com.sun.jersey.api.client.WebResource;
import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.flow.FlowConfig;
import de.tuberlin.cit.sdn.opendaylight.model.flow.Flows;
import de.tuberlin.cit.sdn.opendaylight.model.node.Node;

public class FlowProgrammerClient extends AbstractClient {

    public FlowProgrammerClient(OdlSettings _settings) {
        super(_settings);
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