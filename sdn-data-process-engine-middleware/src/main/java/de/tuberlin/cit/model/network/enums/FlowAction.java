package de.tuberlin.cit.model.network.enums;

public enum FlowAction {
    DROP("DROP"),
    LOOPBACK("LOOPBACK");

    private final String value;

    FlowAction(final String v) {
        this.value = v;
    }

    public String toString(){
        return this.value;
    }
}
