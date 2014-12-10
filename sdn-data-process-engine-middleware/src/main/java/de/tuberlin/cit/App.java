package de.tuberlin.cit;

import de.tuberlin.cit.sdn.opendaylight.FlowClient;
import org.apache.commons.cli.*;

import org.opendaylight.controller.flowprogrammer.northbound.FlowConfigs;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.tools.clientgen.GetResponse;


/**
 * Main Class a.k.a. Entry Point.
 */
public class App
{
    public static void main(String[] args)
    {
        String odlIP = "127.0.0.1";
        String odlUser = "admin";
        String odlPw = "admin";

        System.out.println("Hallo");
        Options options = new Options();
        options.addOption("a", true, "IP address of controller. Default: 127.0.0.1");
        options.addOption("u", true, "Opendaylight username (default: 'admin')");
        options.addOption("p", true, "Opendaylight password (default: 'admin')");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try
        {
            cmd = parser.parse(options, args);
            if(cmd.hasOption("a"))
            {
                odlIP = cmd.getOptionValue("a").toString();
            }
            if(cmd.hasOption("u"))
            {
                odlUser = cmd.getOptionValue("u").toString();
            }
            if(cmd.hasOption("p"))
            {
                odlPw = cmd.getOptionValue("p").toString();
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        System.out.println("Using controller @ " + odlIP);
        System.out.println("Using Opendaylight user '" + odlUser + "'");
        System.out.println("Using password '" + odlPw + "'");

        // ---------------------------
        // Sample Flow retrieval
        FlowClient fc = new FlowClient(odlIP, odlUser, odlPw);

        GetResponse<FlowConfigs> r1 = fc.getStaticFlowsForContainer("default");
        for (FlowConfig fconfig : r1.getEntity().getFlowConfig()) {
            System.out.println("--------- " + fconfig.getName());
        }
        // ---------------------------
    }
}
