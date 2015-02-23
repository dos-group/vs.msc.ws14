package de.tuberlin.cit.sdn.middleware;

import de.tuberlin.cit.sdn.middleware.flinkinterface.RestServer;
import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.helium.client.TopologyClient;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        OdlSettings settings = Utils.getInstance().readSettings(args);

        TopologyClient topoClient = new TopologyClient(settings);
        topoClient.getTopologies();

        RestServer rs = new RestServer();
        rs.startServer();
    }
}
