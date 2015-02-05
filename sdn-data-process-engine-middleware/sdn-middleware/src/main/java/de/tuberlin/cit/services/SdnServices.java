package de.tuberlin.cit.services;

/**
 * Created by Nico on 07.01.2015.
 */

import de.tuberlin.cit.graph.HostGroupCollection;
import de.tuberlin.cit.services.interfaces.SdnCoupler;
import org.apache.flink.runtime.executiongraph.ExecutionGraph;
import org.apache.flink.runtime.instance.HardwareDescription;
import org.apache.flink.runtime.instance.Instance;
import org.apache.flink.runtime.instance.InstanceConnectionInfo;
import org.apache.flink.runtime.instance.InstanceID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SdnServices extends UnicastRemoteObject implements SdnCoupler {

    Logger logger = LogManager.getLogger("SDNMiddlewareLogger");

    public SdnServices() throws RemoteException {

    }

    @Override
    public String getExecutionHost() throws RemoteException {
        logger.debug("getExecutionHost() called");

        // retrieve host from hostgroup coll

        // Dummy data
        return "10.0.0.2";
    }

    /**
     * to test RMI connection
     * @return always true
     * @throws RemoteException
     */
    @Override
    public boolean canConnect() throws RemoteException {
        logger.debug("canConnect() called");
        return true;
    }

    /**
     *
     * @param host hosts ip address
     * @throws RemoteException
     */
    @Override
    public void markInstanceAsInUse(String host) throws RemoteException {
        logger.debug("markInstanceAsInUse() called");
    }

    /**
     *
     * @param host hosts ip address
     * @throws RemoteException
     */
    @Override
    public void markInstanceAsUnused(String host) throws RemoteException {
        logger.debug("markInstanceAsUnused() called");
    }
}
