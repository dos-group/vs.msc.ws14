package de.tuberlin.cit.sdn.opendaylight.helium.model.topology;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    @JsonProperty("link-id") public String id;
    public Destination destination;
    public Source source;

    public static class Destination {
        @JsonProperty("dest-node") public String node;
        @JsonProperty("dest-tp") public String port;
    }

    public static class Source {
        @JsonProperty("source-node") public String node;
        @JsonProperty("source-tp") public String port;
    }
}
