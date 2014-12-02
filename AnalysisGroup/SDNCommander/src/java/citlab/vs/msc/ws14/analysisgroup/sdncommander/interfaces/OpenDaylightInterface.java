package citlab.vs.msc.ws14.analysisgroup.sdncommander.interfaces;

/**
 * Created by Nico on 26.11.2014.
 *
 * To be used when implementing OpenDaylight connections
 */
public interface OpenDaylightInterface {

    /**
     * Checks if controller is reachable
     * @return connection available or not
     */
    boolean testConnection();

    /**
     *
     * @param container name of the container (default one is "default")
     * @return SDN controller output (json)
     */
    String GetTopologyRaw(String container);
}
