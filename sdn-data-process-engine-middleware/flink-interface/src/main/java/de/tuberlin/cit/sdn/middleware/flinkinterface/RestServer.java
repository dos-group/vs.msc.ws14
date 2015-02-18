package de.tuberlin.cit.sdn.middleware.flinkinterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.get;

public class RestServer {
    Logger logger = LogManager.getLogger("flinkinterface-logger");

    public void startServer() {
        logger.debug("Starting REST interfaces");

        get("/hello", (req, res) -> "Hello World");

        logger.debug("REST interface running");
    }
}
