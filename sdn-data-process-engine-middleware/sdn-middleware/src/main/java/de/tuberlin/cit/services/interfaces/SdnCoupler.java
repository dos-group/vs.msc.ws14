package de.tuberlin.cit.services.interfaces;

/**
 * Created by Nico on 07.01.2015.
 */

import org.apache.flink.runtime.executiongraph.ExecutionGraph;
import org.apache.flink.runtime.instance.Instance;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SdnCoupler extends Remote {

    public String getExecutionHost() throws RemoteException;

    public boolean canConnect() throws RemoteException;

    public void sendExecutionGraph(ExecutionGraph eg) throws RemoteException;

    public void markInstanceAsInUse(String host) throws RemoteException;

    public void markInstanceAsUnused(String host) throws RemoteException;
}

