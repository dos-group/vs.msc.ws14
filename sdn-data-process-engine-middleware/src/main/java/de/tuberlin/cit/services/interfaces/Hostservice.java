package de.tuberlin.cit.services.interfaces;

/**
 * Created by Nico on 07.01.2015.
 */

import java.rmi.*;

public interface Hostservice extends Remote{

    public String getGoodHost() throws RemoteException;

}
