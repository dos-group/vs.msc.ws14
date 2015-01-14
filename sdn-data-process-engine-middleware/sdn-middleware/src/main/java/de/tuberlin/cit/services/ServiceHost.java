package de.tuberlin.cit.services;

/**
 * Created by Nico on 07.01.2015.
 */

import de.tuberlin.cit.services.interfaces.Hostservice;
import org.apache.flink.runtime.instance.HardwareDescription;
import org.apache.flink.runtime.instance.Instance;
import org.apache.flink.runtime.instance.InstanceConnectionInfo;
import org.apache.flink.runtime.instance.InstanceID;

import java.net.*;
import java.rmi.*;
import java.rmi.server.*;

public class ServiceHost extends UnicastRemoteObject implements Hostservice {

    public ServiceHost () throws RemoteException {

    }

    @Override
    public Instance getExecutionHost() throws RemoteException {
        int dataPort = 1337;
        int ipcPort = 1338;
        String hostname = "127.0.0.1";

        InstanceConnectionInfo conInfo = null;
        try {
            conInfo = new InstanceConnectionInfo(InetAddress.getByName(hostname), ipcPort, dataPort);
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
        }

        int cpuCores = 2;
        long physMem = 4096;
        long jvmHeap = 1024;
        long managedMem = 1024;

        HardwareDescription hwd = new HardwareDescription(cpuCores,physMem,jvmHeap,managedMem);

        InstanceID iId = new InstanceID();
        int slots = 1;
        Instance instance = new Instance(conInfo, iId, hwd, slots);

        return instance;
    }

    @Override
    public boolean canConnect() throws RemoteException {
        return true;
    }
}
