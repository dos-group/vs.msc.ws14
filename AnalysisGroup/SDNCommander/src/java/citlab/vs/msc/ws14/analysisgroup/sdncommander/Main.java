package citlab.vs.msc.ws14.analysisgroup.sdncommander;

import citlab.vs.msc.ws14.analysisgroup.sdncommander.connectors.ConnectorController;
import citlab.vs.msc.ws14.analysisgroup.sdncommander.connectors.RESTConnector;
import citlab.vs.msc.ws14.analysisgroup.sdncommander.connectors.SharedObjectConnector;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args)
    {
        // SDN Controller connection object
        ConnectorController connCtrl;

        try
        {
            Options options = new Options();
            options.addOption("c", true, "type of connection: REST or SHARED");
            options.addOption("a", true, "IP address of controller (only valid for REST). Default: 127.0.0.1");

            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("c"))
            {
                // REST connection chosen
                if(cmd.getOptionValue("c").equals("REST"))
                {
                    System.out.println("Using REST connection");
                    if(cmd.hasOption("a"))
                    {
                        Statics.ControllerIP = cmd.getOptionValue("a");
                    }
                    connCtrl = new ConnectorController(RESTConnector.getInstance());
                    System.out.println("Topology: " + connCtrl.GetConnector().GetTopologyRaw("default"));
                    System.out.println("Connection: " + connCtrl.GetConnector().testConnection());
                }
                // Else connect via shared objects
                else
                {
                    System.out.println("Using Shared Objects connection");
                    // example calls
                    // ToDo: Remove
                    connCtrl = new ConnectorController(SharedObjectConnector.getInstance());
                    System.out.println(connCtrl.GetConnector().GetTopologyRaw("default"));
                    System.out.println("Not yet implemented");
                    throw new Exception("Not yet implemented");
                }
            }
            else
            {
                System.out.println("No SDN controller connection type specified");
                System.exit(1);
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception while parsing command line args");
            System.exit(2);
        }
    }
}
