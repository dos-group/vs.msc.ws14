package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.model.flow.Flows;

public class FlowProgrammerClient extends AbstractClient {

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
}
