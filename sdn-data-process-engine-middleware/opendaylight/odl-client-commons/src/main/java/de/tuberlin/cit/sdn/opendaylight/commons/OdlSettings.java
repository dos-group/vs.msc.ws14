package de.tuberlin.cit.sdn.opendaylight.commons;

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

    public String getUsername() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getUrl() {
        return "http://" + ip + ":" + port;
    }
}
