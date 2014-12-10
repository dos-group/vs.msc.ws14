package de.tuberlin.cit.sdn.opendaylight;

import de.tuberlin.cit.sdn.opendaylight.interfaces.Flows;
import org.opendaylight.controller.flowprogrammer.northbound.FlowConfigs;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.tools.client.rest.Config;
import org.opendaylight.tools.client.rest.FlowprogrammerHelper;
import org.opendaylight.tools.clientgen.GetResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Created by Nico on 08.12.2014.
 */
public class FlowClient implements Flows {

    private String _hostIP;
    private String _username;
    private String _password;

    Config config = null;
    FlowprogrammerHelper flowHelper = null;

    public FlowClient(String hostIP, String username, String password) {

        _hostIP = hostIP;
        _username = username;
        _password = password;

        config = new Config();
        config.setUsername(username);
        config.setPassword(password);
        config.setAdminUrl("http://" + _hostIP + ":8080");

        flowHelper = new FlowprogrammerHelper();
        flowHelper.setConfig(config);
    }

    public void addFlow(String container, String name, String nodeType, String nodeId, FlowConfig flowConfig)
    {
        throw new NotImplementedException();
    }

    public void modifyFlow(String container, String name, String nodeType, String nodeId, FlowConfig flowConfig)
    {
        throw new NotImplementedException();
    }

    public void deleteFlow(String container, String name, String nodeType, String nodeId)
    {
        throw new NotImplementedException();
    }

    public FlowConfig getStaticFlow(String container, String nodeType, String nodeId, String name)
    {
        throw new NotImplementedException();
    }

    public GetResponse<FlowConfigs> getStaticFlowsForContainer(String container)
    {
        return flowHelper.getStaticFlows(container);
    }

    public GetResponse<FlowConfigs> getStaticFlows(String container, String nodeType, String nodeId)
    {
        throw new NotImplementedException();
    }
}