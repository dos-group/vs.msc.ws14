package de.tuberlin.cit.sdn.opendaylight.interfaces;

import org.opendaylight.controller.flowprogrammer.northbound.FlowConfigs;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.tools.clientgen.GetResponse;

/**
 * Created by Nico on 08.12.2014.
 */



public interface Flows {

    void AddFlow(String container, String name, String nodeType, String nodeId, FlowConfig flowConfig);

    void ModifyFlow(String container, String name, String nodeType, String nodeId, FlowConfig flowConfig);

    void DeleteFlow(String container, String name, String nodeType, String nodeId);

    FlowConfig GetStaticFlow(String container, String nodeType, String nodeId, String name);

    GetResponse<FlowConfigs> GetStaticFlowsForContainer(String container);

    GetResponse<FlowConfigs> GetStaticFlows(String container, String nodeType, String nodeId);
}
