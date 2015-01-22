package de.tuberlin.cit.graph.model;

import java.util.HashSet;
import java.util.Set;

public class HostGroup {

    private String id;

    private Set<Host> hosts;

    private NetworkDevice networkDevice;

    public HostGroup(NetworkDevice device) {
        this.networkDevice = device;
        this.id = device.getId();
        this.hosts = new HashSet<>();
    }

    public String getId() {
        return this.id;
    }

    public Set<Host> getHosts() {
        return hosts;
    }

    public void addHost(Host host) {
        this.hosts.add(host);
    }

    public boolean hasFreeHosts() {
        for (Host host : this.hosts) {
            if (host.isFree()) {
                return true;
            }
        }
        return false;
    }

    public int numberOfFreeHosts() {
        int n = 0;
        for (Host host : this.hosts) {
            if (host.isFree()) {
                n++;
            }
        }
        return n;
    }

    public NetworkDevice getNetworkDevice() {
        return networkDevice;
    }

    public Host getFreeHost() {
        Host freeHost = null;
        for (Host host : this.hosts) {
            if (host.isFree()) {
                freeHost = host;
                break;
            }
        }
        return freeHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HostGroup hostGroup = (HostGroup) o;

        if (!id.equals(hostGroup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isBusy() {
        for (Host host : this.hosts) {
            if (host.isFree()) {
                return false;
            }
        }
        return true;
    }
}
