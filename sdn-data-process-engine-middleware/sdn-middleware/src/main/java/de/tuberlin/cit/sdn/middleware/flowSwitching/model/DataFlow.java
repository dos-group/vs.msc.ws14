package de.tuberlin.cit.sdn.middleware.flowSwitching.model;


import java.util.LinkedList;

public class DataFlow {

    private String sourceHostId;
    private String sinkHostId;
    private LinkedList<String> path;

    public DataFlow(String sourceHostId, String sinkHostId) {
        this.sourceHostId = sourceHostId;
        this.sinkHostId = sinkHostId;
    }

    public void setPath(LinkedList<String> path) {
        this.path = path;
    }

    public LinkedList<String> getPath() {
        return this.path;
    }

}
