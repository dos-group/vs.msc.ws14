package de.tuberlin.cit.sdn.opendaylight.interfaces;

import org.opendaylight.controller.flowprogrammer.northbound.FlowConfigs;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.tools.clientgen.GetResponse;

/**
 * Created by Nico on 08.12.2014.
 */



public interface Flows {

    void addFlow(String container, String name, String nodeType, String nodeId, FlowConfig flowConfig);

    void modifyFlow(String container, String name, String nodeType, String nodeId, FlowConfig flowConfig);

    void deleteFlow(String container, String name, String nodeType, String nodeId);

    FlowConfig getStaticFlow(String container, String nodeType, String nodeId, String name);

    GetResponse<FlowConfigs> getStaticFlowsForContainer(String container);

    GetResponse<FlowConfigs> getStaticFlows(String container, String nodeType, String nodeId);
}
