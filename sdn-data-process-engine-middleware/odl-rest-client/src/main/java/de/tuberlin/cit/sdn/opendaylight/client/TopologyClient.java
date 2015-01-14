package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.topology.Topology;

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
