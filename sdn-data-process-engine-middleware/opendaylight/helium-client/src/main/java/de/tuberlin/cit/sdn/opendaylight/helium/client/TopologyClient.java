package de.tuberlin.cit.sdn.opendaylight.helium.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.cit.sdn.opendaylight.commons.AbstractClient;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.helium.model.topology.Topology;

import java.io.IOException;
import java.util.List;

public class TopologyClient extends AbstractClient {

    public TopologyClient(OdlSettings settings) {
        super(settings);
    }

    public TopologyClient() {
        super();
    }

    @Override
    public String getBaseUrl() {
        return "/restconf/operational/network-topology:network-topology";
    }

    public List<Topology> getTopologies() throws IOException {
        String s = resource("").get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(s);

        node = node.get("network-topology").get("topology");
        List<Topology> list = mapper.readValue(node.traverse(), new TypeReference<List<Topology>>() {});
        return list;
    }
}
