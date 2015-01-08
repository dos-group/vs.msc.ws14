package de.tuberlin.cit.services.interfaces;

/**
 * Created by Nico on 07.01.2015.
 */

import org.apache.flink.runtime.instance.Instance;

import java.rmi.*;

public interface Hostservice extends Remote{

    public Instance getExecutionHost() throws RemoteException;

    public boolean canConnect() throws RemoteException;
}

