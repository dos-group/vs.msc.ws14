package citlab.vs.msc.ws14.analysisgroup.sdncommander.connectors;

import citlab.vs.msc.ws14.analysisgroup.sdncommander.Statics;
import citlab.vs.msc.ws14.analysisgroup.sdncommander.interfaces.OpenDaylightInterface;

import java.net.InetAddress;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by Nico on 26.11.2014.
 */
public class RESTConnector implements OpenDaylightInterface {

    private static RESTConnector ourInstance = new RESTConnector();
    private String controllerIP;

    // SDN Controller login
    //ToDo: change...
    String sdnName = "admin";
    String sdnPassword = "admin";

    /**
     * Singleton Getter
     * @return the instance
     */
    public static RESTConnector getInstance() {
        return ourInstance;
    }

    /**
     * Ctor
     */
    private RESTConnector() {
        controllerIP = Statics.ControllerIP;
        System.out.println("Using Controller IP " + controllerIP);
    }

    public boolean testConnection() {
        try {
            InetAddress ip = InetAddress.getByName(controllerIP);
            byte[] ipAsBytes = ip.getAddress();

            // 1s
            return InetAddress.getByAddress(ipAsBytes).isReachable(5000);

        } catch (Exception e) {
            return false;
        }
    }


    public String GetTopologyRaw(String container)
    {
        String output = "";
        try {

            URL url = new URL("http://" + controllerIP + ":8080/controller/nb/v2/topology/" + container);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            String authString = sdnName + ":" + sdnPassword;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);

            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            String toRead;
            while ((toRead = br.readLine()) != null) {
                output += toRead;
            }

            conn.disconnect();


        } catch (Exception e) {

            e.printStackTrace();

        }
        return output;
    }
}
