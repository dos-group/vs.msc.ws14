package de.tuberlin.cit.sdn.middleware.flowSwitching;

import de.tuberlin.cit.sdn.middleware.flowSwitching.model.Link;
import de.tuberlin.cit.sdn.middleware.flowSwitching.model.Switch;
import de.tuberlin.cit.sdn.middleware.flowSwitching.model.DataFlowBundle;

import java.util.LinkedList;


public class LoadBalancer {

    public static void balanceAll(LinkedList<Switch> switches, LinkedList<Link> links){

        // first filter all switches that are directly connected to hosts
        LinkedList<Switch> hostSwitches = filterHostSwitches(switches);

        // then create all n(n-1) taskFlowBundles of the n hostSwitches
        LinkedList<DataFlowBundle> allBundles = createDataFlowBundles(hostSwitches);

        // do the following per bundle
        for (DataFlowBundle bundle : allBundles) {
            // compute up to "amount of taskFlows" distinct paths
            bundle.computePaths(switches, links);
            // assign the taskFlows to the paths
            bundle.assignDataFlows();
        }
    }

    private static LinkedList<Switch> filterHostSwitches(LinkedList<Switch> allSwitches){
        LinkedList<Switch> hostSwitches = new LinkedList<Switch>();
        for (int i=0; i<allSwitches.size(); i++) {
            Switch current = allSwitches.get(i);
            if (!current.getHostIds().isEmpty()) {
                hostSwitches.add(current);
            }
        }
        return hostSwitches;
    }

    private static LinkedList<DataFlowBundle> createDataFlowBundles(LinkedList<Switch> hostSwitches) {
        LinkedList<DataFlowBundle> bundles = new LinkedList<DataFlowBundle>();
        for (Switch sourceSwitch : hostSwitches) {
            for (Switch sinkSwitch : hostSwitches) {
                if (!sourceSwitch.getId().equals(sinkSwitch.getId())) {
                    bundles.add(new DataFlowBundle(sourceSwitch, sinkSwitch));
                }
            }
        }
        return bundles;
    }

}
