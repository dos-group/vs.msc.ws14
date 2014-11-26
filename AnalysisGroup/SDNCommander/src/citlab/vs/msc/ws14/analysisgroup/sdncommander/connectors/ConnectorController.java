package citlab.vs.msc.ws14.analysisgroup.sdncommander.connectors;

import citlab.vs.msc.ws14.analysisgroup.sdncommander.interfaces.OpenDaylightInterface;

/**
 * Created by Nico on 26.11.2014.
 *
 * Handles the right sdn controller connection mode
 * is an implementation of the strategy pattern
 */

public class ConnectorController {

    private static OpenDaylightInterface odlInterface;

    /**
     * Ctor
     */
    public ConnectorController(OpenDaylightInterface odlC) {
        this.odlInterface = odlC;
    }

    public OpenDaylightInterface GetConnector()
    {
        return odlInterface;
    }
}
