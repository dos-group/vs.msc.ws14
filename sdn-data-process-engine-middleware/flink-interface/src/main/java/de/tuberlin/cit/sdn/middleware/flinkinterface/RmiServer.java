package de.tuberlin.cit.sdn.middleware.flinkinterface;

import de.tuberlin.cit.sdn.middleware.flinkinterface.services.SdnServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.Naming;

public class RmiServer {
    Logger logger = LogManager.getLogger("flinkinterface-logger");

    public void startServer() {
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
