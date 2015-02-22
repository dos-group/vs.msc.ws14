package de.tuberlin.cit.sdn.opendaylight.hydrogen.client;

import de.tuberlin.cit.sdn.opendaylight.commons.AbstractClient;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.topology.Topology;

public class TopologyClient extends AbstractClient {

    public TopologyClient(OdlSettings settings) {
        super(settings);
    }

    public TopologyClient() {
        super();
    }

    @Override
    public String getBaseUrl() {
        return "/controller/nb/v2/topology/";
    }

    public Topology getTopology() {
        return getTopology("default");
    }

    public Topology getTopology(String container) {
        Topology topology = resource(container).get(Topology.class);
        return topology;
    }
}
