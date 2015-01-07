package de.tuberlin.cit.services;

/**
 * Created by Nico on 07.01.2015.
 */

import de.tuberlin.cit.services.interfaces.Hostservice;

import java.rmi.*;
import java.rmi.server.*;

public class ServiceHost extends UnicastRemoteObject implements Hostservice {

    public ServiceHost () throws RemoteException {

    }
    @Override
    public String getGoodHost() throws RemoteException {
        return "This is a nice host to work with!";
    }
}
