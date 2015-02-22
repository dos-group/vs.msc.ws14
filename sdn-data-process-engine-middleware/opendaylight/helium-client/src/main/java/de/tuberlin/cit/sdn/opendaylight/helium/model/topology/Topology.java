package de.tuberlin.cit.sdn.opendaylight.helium.model.topology;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Topology {
    @JsonProperty("topology-id") public String id;
    @JsonProperty("node") public List<Node> nodes;
    @JsonProperty("link") public List<Link> links;
}
