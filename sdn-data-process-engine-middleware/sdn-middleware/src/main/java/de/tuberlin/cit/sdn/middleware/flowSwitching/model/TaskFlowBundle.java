package de.tuberlin.cit.sdn.middleware.flowSwitching.model;

import java.util.LinkedList;


public class TaskFlowBundle {

    private String fromSwitchId;
    private String toSwitchId;
    private LinkedList<TaskFlow> taskFlows;
    // a path is a LinkedList of switchIds
    private LinkedList<LinkedList<String>> distinctPaths;
    
    public TaskFlowBundle(Switch fromSwitch, Switch toSwitch) {

        this.fromSwitchId = new String(fromSwitch.getId());
        this.toSwitchId = new String(toSwitch.getId());

        this.taskFlows = new LinkedList<TaskFlow>();

        for (String fromHostId : fromSwitch.getHostIds()) {
            for (String toHostId : toSwitch.getHostIds()) {
                taskFlows.add(new TaskFlow(fromHostId, toHostId));
            }
        }
    }

    public String getFromSwitchId() {
        return this.fromSwitchId;
    }

    public String getToSwitchId() {
        return this.toSwitchId;
    }

    public LinkedList<TaskFlow> getTaskFlows() {
        return this.taskFlows;
    }

    public void computeDistinctPaths(LinkedList<Switch> switches, LinkedList<Link> links, int maxAmount) {
        return;
    }

    public void assignTaskFlows() {
        return;
    }

}
