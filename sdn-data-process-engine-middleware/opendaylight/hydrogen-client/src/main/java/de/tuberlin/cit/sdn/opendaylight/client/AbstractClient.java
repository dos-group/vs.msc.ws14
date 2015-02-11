package de.tuberlin.cit.sdn.opendaylight.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;

import javax.ws.rs.core.MediaType;

abstract class AbstractClient {
    protected OdlSettings settings;
    protected Client client;

    public AbstractClient(OdlSettings settings) {
        this.settings = settings;

        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(settings.getUsername(), settings.getPassword()));
    }

    public AbstractClient() {
        this(new OdlSettings());
    }

    public abstract String getBaseUrl();

    protected WebResource.Builder resource(String path) {
        return client.resource(settings.getUrl() + getBaseUrl() + path)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);
    }
}
