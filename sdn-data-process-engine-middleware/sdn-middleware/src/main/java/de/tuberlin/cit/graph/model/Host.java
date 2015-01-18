package de.tuberlin.cit.graph.model;

public class Host extends NetworkVertex {

    private String macAddress;

    public Host(String id) {
        super(id);
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
