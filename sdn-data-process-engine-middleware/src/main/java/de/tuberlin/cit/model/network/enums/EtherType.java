package de.tuberlin.cit.model.network.enums;

public enum EtherType {
    IPv4("0x0800"),
    IPv6("0x86DD");

    private final String value;

    EtherType(final String v) {
        this.value = v;
    }

    public String toString() {
        return value;
    }
}
