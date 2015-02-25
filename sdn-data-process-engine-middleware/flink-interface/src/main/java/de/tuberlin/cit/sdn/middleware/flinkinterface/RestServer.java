package de.tuberlin.cit.sdn.middleware.flinkinterface;

import de.tuberlin.cit.sdn.middleware.flinkinterface.services.SdnServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class RestServer {
    Logger logger = LogManager.getLogger("flinkinterface-logger");
    SdnServices services;

    public RestServer(SdnServices services) {
        this.services = services;
    }

    public void startServer() {
        logger.debug("Starting REST interfaces");

        get("/hello", (req, res) -> "It works");

        get("/getExecutionHost", (req, res) -> services.getExecutionHost());

        post("/markInstanceAsUnused", (req, res) -> {
            services.markInstanceAsUnused(req.body());
            return "ok";
        });

        logger.debug("REST interface running");
    }
}
