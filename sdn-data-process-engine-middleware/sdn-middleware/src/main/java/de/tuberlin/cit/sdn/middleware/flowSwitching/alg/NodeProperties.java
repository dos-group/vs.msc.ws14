package de.tuberlin.cit.sdn.middleware.flowSwitching.alg;


public class NodeProperties {

    public String switchId;
    public NodeProperties predecessor;
    public int distance;

    public NodeProperties(String switchId, int distance) {
        this.switchId = switchId;
        this.predecessor = null;
        this.distance = distance;
    }
}
