package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.model.host.Hosts;

public class HostTrackerClient extends AbstractClient {

    @Override
    public String getBaseUrl() {
        return "/controller/nb/v2/hosttracker/";
    }

    public Hosts getActiveHosts() {
        return getActiveHosts("default");
    }

    public Hosts getActiveHosts(String container) {
        Hosts hosts = resource(container + "/hosts/active").get(Hosts.class);
        return hosts;
    }

    public Hosts getInactiveHosts() {
        return getInactiveHosts("default");
    }

    public Hosts getInactiveHosts(String container) {
        Hosts hosts = resource(container + "/hosts/inactive").get(Hosts.class);
        return hosts;
    }
}
