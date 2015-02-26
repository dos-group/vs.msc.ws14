package de.tuberlin.cit.sdn.middleware.flowSwitching;

import de.tuberlin.cit.sdn.middleware.flowSwitching.model.Link;
import de.tuberlin.cit.sdn.middleware.flowSwitching.model.Switch;
import de.tuberlin.cit.sdn.middleware.flowSwitching.model.TaskFlowBundle;

import java.util.LinkedList;


public class LoadBalancer {

    public static void balanceAll(LinkedList<Switch> switches, LinkedList<Link> links){

        // first filter all switches that are directly connected to hosts
        LinkedList<Switch> hostSwitches = filterHostSwitches(switches);

        // then create all n(n-1) taskFlowBundles of the n hostSwitches
        LinkedList<TaskFlowBundle> allBundles = createTaskFlowBundles(hostSwitches);

        // do the following per bundle
        for (TaskFlowBundle bundle : allBundles) {
            // compute up to "amount of taskFlows" distinct paths
            bundle.computeDistinctPaths(switches, links, bundle.getTaskFlows().size());
            // assign the taskFlows to the paths
            bundle.assignTaskFlows();
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

    private static LinkedList<TaskFlowBundle> createTaskFlowBundles(LinkedList<Switch> hostSwitches) {
        LinkedList<TaskFlowBundle> bundles = new LinkedList<TaskFlowBundle>();
        for (Switch fromSwitch : hostSwitches) {
            for (Switch toSwitch : hostSwitches) {
                bundles.add(new TaskFlowBundle(fromSwitch, toSwitch));
            }
        }
        return bundles;
    }

}
