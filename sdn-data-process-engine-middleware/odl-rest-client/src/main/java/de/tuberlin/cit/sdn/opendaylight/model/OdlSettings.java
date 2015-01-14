package de.tuberlin.cit.sdn.opendaylight.model;

/**
 * Created by Nico on 02.01.2015.
 */
public class OdlSettings {

    private String ip = "localhost";
    private String user = "admin";
    private String password = "admin";
    private String port = "8080";
    private boolean demandDemo = false;
    private int toggleTime = 5000;

    public OdlSettings(String ip, String user, String password, String port, boolean demandDemo, int toggleTime) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        this.demandDemo = demandDemo;
        this.toggleTime = toggleTime;
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

    public String getUrl(){
        return "http://" + ip + ":" + port;
    }

    public int getToggleTime() { return toggleTime; }
}
