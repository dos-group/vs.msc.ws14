package de.tuberlin.cit.sdn.middleware;

import de.tuberlin.cit.sdn.middleware.services.SdnServices;
import org.apache.log4j.Logger;

import java.rmi.Naming;

public class RmiServer {
    private Logger logger = Logger.getLogger(this.getClass());

    public static void main(String args[]) {
        new RmiServer().execute(args);
    }

    private void execute(String args[]) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            logger.debug("RMI registry ready.");
            Naming.rebind("SdnCoupler", new SdnServices());
            logger.debug("RMI server ready.");
        } catch (Exception e) {
            logger.error("Failed running rmi server.", e);
        }
    }
}
