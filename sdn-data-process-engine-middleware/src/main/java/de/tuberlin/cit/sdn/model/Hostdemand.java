package de.tuberlin.cit.sdn.model;

import de.tuberlin.cit.sdn.opendaylight.model.host.HostConfig;

import java.util.List;

/**
 * Created by Nico on 02.01.2015.
 */
public class Hostdemand {
    private HostConfig from;
    private List<HostConfig> to;

    public Hostdemand(HostConfig from, List<HostConfig> to) {
        this.to = to;
        this.from = from;
    }

    public HostConfig getOriginHost(){
        return from;
    }

    public List<HostConfig> getTargetHosts(){
        return to;
    }
}
