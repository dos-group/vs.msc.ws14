package de.tuberlin.cit.sdn.model;

import java.util.List;

/**
 * Created by Nico on 02.01.2015.
 */
public class Demands {

    private static Demands instance = null;
    private List<Hostdemand> currentDemand = null;
    private List<Hostdemand> futureDemand = null;

    private Demands() {
    }

    public static Demands getInstance() {
        if (instance == null)
            instance = new Demands();
        return instance;
    }

    public void stepForward() {
        this.currentDemand = this.futureDemand;
        this.futureDemand = null;
    }

    public void stepForward(List<Hostdemand> newFutureDemand) {
        this.currentDemand = this.futureDemand;
        this.futureDemand = newFutureDemand;
    }

    public boolean hasFutureDemand() {
        return this.futureDemand == null ? true : false;
    }

    public boolean hasCurrentDemand() {
        return this.currentDemand == null ? true : false;
    }

    public List<Hostdemand> getCurrentDemand() {
        return this.currentDemand;
    }

    public void setCurrentDemand(List<Hostdemand> currentDemand) {
        this.currentDemand = currentDemand;
    }

    public List<Hostdemand> getFutureDemand() {
        return this.futureDemand;
    }

    public void setFutureDemand(List<Hostdemand> futureDemand) {
        this.futureDemand = futureDemand;
    }

    public void clearCurrentDemand() {
        this.currentDemand = null;
    }

    public void clearFutureDemand() {
        this.futureDemand = null;
    }

    public void clearAllDemands() {
        this.futureDemand = null;
        this.currentDemand = null;
    }
}
