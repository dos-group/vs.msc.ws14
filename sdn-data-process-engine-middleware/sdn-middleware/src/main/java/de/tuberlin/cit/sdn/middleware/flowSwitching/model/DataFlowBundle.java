package de.tuberlin.cit.sdn.middleware.flowSwitching.model;

import de.tuberlin.cit.sdn.middleware.flowSwitching.alg.YenAlgorithm;

import java.util.LinkedList;


public class DataFlowBundle {

    private String sourceSwitchId;
    private String sinkSwitchId;
    private LinkedList<DataFlow> dataFlows;
    // a path is a LinkedList of switchIds
    private LinkedList<LinkedList<String>> kShortestPaths;
    
    public DataFlowBundle(Switch sourceSwitch, Switch sinkSwitch) {

        this.sourceSwitchId = sourceSwitch.getId();
        this.sinkSwitchId = sinkSwitch.getId();

        this.dataFlows = new LinkedList<DataFlow>();

        for (String fromHostId : sourceSwitch.getHostIds()) {
            for (String toHostId : sinkSwitch.getHostIds()) {
                dataFlows.add(new DataFlow(fromHostId, toHostId));
            }
        }
    }

    // using yen's algorithm compute the top k shortest paths from source to sink
    // where k is the amount of data flows in this bundle
    public void computePaths(LinkedList<Switch> switches, LinkedList<Link> links) {
        this.kShortestPaths = YenAlgorithm.run(switches, links, this.sourceSwitchId, this.sinkSwitchId, this.dataFlows.size());
    }

    public void assignDataFlows() {
        return;
    }

}
