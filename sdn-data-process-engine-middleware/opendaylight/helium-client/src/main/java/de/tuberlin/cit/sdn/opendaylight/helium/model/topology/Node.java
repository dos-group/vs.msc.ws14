package de.tuberlin.cit.sdn.opendaylight.helium.model.topology;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {
    @JsonProperty("node-id") public String id;
    @JsonProperty("host-tracker-service:addresses") public List<Address> addresses;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        public String id;
        public String ip;
        public String mac;
    }
}
