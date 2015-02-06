package de.tuberlin.cit.sdn.middleware.graph.model;

public class Host extends NetworkVertex {

    private String macAddress;
    private boolean free = true;

    public Host(String id) {
        super(id);
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
