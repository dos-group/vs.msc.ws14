package citlab.vs.msc.ws14.analysisgroup.sdncommander.connectors;

import citlab.vs.msc.ws14.analysisgroup.sdncommander.interfaces.OpenDaylightInterface;

/**
 * Created by Nico on 26.11.2014.
 */
public class SharedObjectConnector implements OpenDaylightInterface {

    private static SharedObjectConnector ourInstance = new SharedObjectConnector();

    /**
     * Singleton Getter
     * @return the instance
     */
    public static SharedObjectConnector getInstance() {
        return ourInstance;
    }

    /**
     * Ctor
     */
    private SharedObjectConnector() {

    }

    public boolean testConnection()
    {
        //ToDo: implement properly
        return true;
    }


    public String GetTopologyRaw(String container)
    {
        //ToDo: implement
        return "Hello from Shared Objects";
    }
}
