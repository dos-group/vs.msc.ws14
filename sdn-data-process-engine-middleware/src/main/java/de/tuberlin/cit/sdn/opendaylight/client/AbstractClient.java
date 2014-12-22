package de.tuberlin.cit.sdn.opendaylight.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import javax.ws.rs.core.MediaType;

abstract class AbstractClient {
    private String url = "http://localhost:8080";
    private String user = "admin";
    private String password = "admin";

    private Client client = Client.create();

    public AbstractClient() {
        client.addFilter(new HTTPBasicAuthFilter(user, password));
    }

    public abstract String getBaseUrl();

    public WebResource.Builder resource(String path) {
        return client.resource(url + getBaseUrl() + path)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);
    }
}
