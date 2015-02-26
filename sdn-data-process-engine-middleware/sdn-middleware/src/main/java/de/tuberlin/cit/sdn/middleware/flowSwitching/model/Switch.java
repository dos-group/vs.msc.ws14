package de.tuberlin.cit.sdn.middleware.flowSwitching.model;

import java.util.LinkedList;


public class Switch {

    private String id;
    private LinkedList<String> hosts;

    public Switch(String id) {
        this(id, new LinkedList<String>());
    }

    public Switch(String id, LinkedList<String> hosts) {
        this.id = id;
        this.hosts = hosts;
    }

    public String getId() {
        return this.id;
    }

    public LinkedList<String> getHostIds() {
        return new LinkedList<String>(this.hosts);
    }

    public Switch copy() {
        return new Switch(this.getId(), this.getHostIds());
    }

}
