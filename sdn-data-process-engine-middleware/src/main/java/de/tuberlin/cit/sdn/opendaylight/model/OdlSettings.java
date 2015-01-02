package de.tuberlin.cit.sdn.opendaylight.model;

/**
 * Created by Nico on 02.01.2015.
 */
public class OdlSettings {

    private String ip = "localhost";
    private String user = "admin";
    private String password = "admin";
    private String port = "8080";

    public OdlSettings(String _ip, String _user, String _password, String _port) {
        ip = _ip;
        port = _port;
        user = _user;
        password = _password;
    }

    public OdlSettings() {
    }

    public String getUsername(){
        return user;
    }

    public String getPassword(){
        return password;
    }

    public String getIp(){
        return ip;
    }

    public String getPort(){
        return port;
    }

    public String GetUrl(){
        return "http://" + ip + ":" + port;
    }
}
