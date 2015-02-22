package de.tuberlin.cit.sdn.opendaylight.helium.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty("link-id") public String id;
    public Destination destination;
    public Source source;

    public class Destination {
        @JsonProperty("dest-node") public String node;
        @JsonProperty("dest-tp") public String port;
    }

    public class Source {
        @JsonProperty("source-node") public String node;
        @JsonProperty("source-tp") public String port;
    }
}
