package de.tuberlin.cit.sdn.opendaylight.helium.model.topology;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    @JsonProperty("link-id") public String id;
    @JsonProperty("destination") public Destination dest;
    @JsonProperty("source") public Source src;

    public static class Destination {
        @JsonProperty("dest-node") public String node;
        @JsonProperty("dest-tp") public String port;

        public String parsePort() {
            return Link.parsePort(node, port);
        }
    }

    public static class Source {
        @JsonProperty("source-node") public String node;
        @JsonProperty("source-tp") public String port;

        public String parsePort() {
            return Link.parsePort(node, port);
        }
    }

    private static String parsePort(String node, String port) {
        if (node.startsWith("host")) {
            return "0";
        } else {
            int i = port.lastIndexOf(":");
            if (i < 0) {
                throw new NumberFormatException("Could not parse port for node " + node);
            }
            return port.substring(i + 1);
        }
    }
}
