package de.tuberlin.cit.sdn.opendaylight.helium.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import de.tuberlin.cit.sdn.opendaylight.helium.model.Topology;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

public class TopologyClient {
    protected String user;
    protected String password;
    protected String url;

    protected Client client;

    public TopologyClient(String user, String password, String url) {
        this.user = user;
        this.password = password;
        this.url = url;

        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(user, password));
    }

    public TopologyClient() {
        this("admin", "admin", "http://localhost:8181/restconf");
    }

    public String getBaseUrl() {
        return "/operational/network-topology:network-topology";
    }

    protected WebResource.Builder resource(String path) {
        return client.resource(url + getBaseUrl() + path)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);
    }

    public List<Topology> getTopologies() throws IOException {
        String s = resource("").get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode node = mapper.readTree(s);

        node = node.get("network-topology").get("topology");
        List<Topology> list = mapper.readValue(node.traverse(), new TypeReference<List<Topology>>() {});
        return list;
    }
}
