package de.tuberlin.cit;

import de.tuberlin.cit.sdn.opendaylight.FlowClient;
import org.apache.commons.cli.*;

import org.opendaylight.controller.flowprogrammer.northbound.FlowConfigs;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.controller.sal.core.Node;
import org.opendaylight.controller.topology.northbound.EdgeProperties;
import org.opendaylight.controller.topology.northbound.Topology;
import org.opendaylight.tools.client.rest.Config;
import org.opendaylight.tools.client.rest.FlowprogrammerHelper;
import org.opendaylight.tools.client.rest.TopologyHelper;
import org.opendaylight.tools.clientgen.GetResponse;
import javax.ws.rs.core.MediaType;


/**
 * Main Class a.k.a. Entry Point.
 */
public class App
{
    public static void main(String[] args)
    {
        String odlIP = "127.0.0.1";

        Options options = new Options();
        options.addOption("a", true, "IP address of controller. Default: 127.0.0.1");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try
        {
            cmd = parser.parse(options, args);
            if(cmd.hasOption("a"))
            {
                odlIP = cmd.getOptionValue("a").toString();
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        System.out.println("Using controller @ " + odlIP);


        // ---------------------------
        // Sample Flow retrieval
        FlowClient fc = new FlowClient(odlIP, "admin", "admin");

        GetResponse<FlowConfigs> r1 = fc.GetStaticFlowsForContainer("default");
        for (FlowConfig fconfig : r1.getEntity().getFlowConfig()) {
            System.out.println("--------- " + fconfig.getName());
        }
        // ---------------------------
    }
}
