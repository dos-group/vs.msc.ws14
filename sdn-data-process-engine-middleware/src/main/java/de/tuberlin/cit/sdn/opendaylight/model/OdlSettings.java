package de.tuberlin.cit.sdn.opendaylight.model;

/**
 * Created by Nico on 02.01.2015.
 */
public class OdlSettings {

    private String ip = "localhost";
    private String user = "admin";
    private String password = "admin";
    private String port = "8080";

    public OdlSettings(String ip, String user, String password, String port) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public OdlSettings() {
    }

    public String GetUsername(){
        return user;
    }

    public String GetPassword(){
        return password;
    }

    public String GetIp(){
        return ip;
    }

    public String GetPort(){
        return port;
    }

    public String GetUrl(){
        return "http://" + ip + ":" + port;
    }
}
