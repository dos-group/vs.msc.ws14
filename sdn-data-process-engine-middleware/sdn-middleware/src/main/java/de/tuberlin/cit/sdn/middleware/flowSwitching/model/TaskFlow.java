package de.tuberlin.cit.sdn.middleware.flowSwitching.model;


public class TaskFlow {

    private String fromHostId;
    private String toHostId;

    public TaskFlow(String fromHostId, String toHostId) {
        this.fromHostId = fromHostId;
        this.toHostId = toHostId;
    }
}
