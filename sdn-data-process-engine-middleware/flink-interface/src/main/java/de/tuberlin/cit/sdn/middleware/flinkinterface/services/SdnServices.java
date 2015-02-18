package de.tuberlin.cit.sdn.middleware.flinkinterface.services;

/**
 * Created by Nico on 07.01.2015.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

public class SdnServices {

   Logger logger = LogManager.getLogger("SDNMiddlewareLogger");

    public SdnServices() {

    }

    public String getExecutionHost() {
        logger.debug("getExecutionHost() called");

        // retrieve host from hostgroup coll

        // Dummy data
        return "10.0.0.2";
    }

    /**
     *
     * @param host hosts ip address
     * @throws RemoteException
     */
    public void markInstanceAsInUse(String host) {
        logger.debug("markInstanceAsInUse() called. Host: " + host);
    }

    /**
     *
     * @param host hosts ip address
     * @throws RemoteException
     */
    public void markInstanceAsUnused(String host) {
       logger.debug("markInstanceAsUnused() called. Host: " + host);
    }
}
