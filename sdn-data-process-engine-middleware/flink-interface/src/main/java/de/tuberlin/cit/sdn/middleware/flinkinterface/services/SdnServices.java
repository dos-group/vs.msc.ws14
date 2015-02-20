package de.tuberlin.cit.sdn.middleware.flinkinterface.services;

/**
 * Created by Nico on 07.01.2015.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

public class SdnServices {

    Logger logger = LogManager.getLogger("SDNMiddlewareLogger");

    int i = 1; // dummy iterator
    public SdnServices() {

    }

    public String getExecutionHost() {
        // retrieve actual host from hostgroup coll
        // Dummy data
        i++;
        String host = "10.0.0." + i;
        logger.debug("getExecutionHost() called; returning  " + host);

        return host;
    }

    /**
     *
     * @param host hosts ip address
     * @throws RemoteException
     */
    public void markInstanceAsUnused(String host) {
        // call actual handling method
        logger.debug("markInstanceAsUnused() called. Host: " + host);
    }
}
