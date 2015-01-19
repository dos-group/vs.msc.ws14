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

    public synchronized Set<Host> occupyNHosts(int n) {
        Set<Host> freeHosts = new HashSet<>();
        if (n > this.numberOfFreeHosts()) {
            throw new RuntimeException("HostGroup " + this.id + " does not have enough free hosts.");
        } else {
            // find free hosts
            for (Host host : this.hosts) {
                if (host.isFree()) {
                    freeHosts.add(host);
                    if (freeHosts.size() == n) {
                        break;
                    }
                }
            }
        }
        // occupy free hosts
        for (Host host : freeHosts) {
            host.setFree(false);
        }
        return freeHosts;
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
}
