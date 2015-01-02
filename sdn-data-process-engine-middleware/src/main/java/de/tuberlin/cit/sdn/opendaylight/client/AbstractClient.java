package de.tuberlin.cit.sdn.opendaylight.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;

import javax.ws.rs.core.MediaType;

abstract class AbstractClient {
    OdlSettings odls = null;

    private Client client = Client.create();

    public AbstractClient(OdlSettings settings) {
        odls = settings;
        client.addFilter(new HTTPBasicAuthFilter(odls.getUsername(), odls.getPassword()));
    }
    public AbstractClient() {
        odls = new OdlSettings();
        client.addFilter(new HTTPBasicAuthFilter(odls.getUsername(), odls.getPassword()));
    }

    public abstract String getBaseUrl();

    public WebResource.Builder resource(String path) {
        return client.resource(odls.GetUrl() + getBaseUrl() + path)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);
    }
}
