package de.tuberlin.cit.sdn.middleware.flinkinterface;

import de.tuberlin.cit.sdn.middleware.flinkinterface.services.SdnServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class RestServer {
    Logger logger = LogManager.getLogger("flinkinterface-logger");

    public void startServer() {
        SdnServices services = new SdnServices();

        logger.debug("Starting REST interfaces");

        get("/hello", (req, res) -> "It works");

        get("/getExecutionHost", (req, res) -> services.getExecutionHost());

        post("/markInstanceAsInUse", (req, res) -> {
            services.markInstanceAsInUse(req.body());
            return "ok";
        });

        post("/markInstanceAsUnused", (req, res) -> {
            services.markInstanceAsUnused(req.body());
            return "ok";
        });

        logger.debug("REST interface running");
    }
}
