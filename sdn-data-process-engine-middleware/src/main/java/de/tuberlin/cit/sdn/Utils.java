package de.tuberlin.cit.sdn;

import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Created by Nico on 02.01.2015.
 *
 * Contains various helper methods.
 */
public class Utils {

    Logger logger = LogManager.getLogger("SDNMiddlewareLogger");
    private static Utils instance = null;

    public static Utils getInstance(){
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    private Utils() {
    }

    public OdlSettings readSettings(String[] args) {
        Options options = new Options();
        options.addOption("ip", true, "IP address of controller. Default: 127.0.0.1");
        options.addOption("u", true, "Opendaylight username (default: 'admin')");
        options.addOption("pw", true, "Opendaylight password (default: 'admin')");
        options.addOption("p", true, "Opendaylight port (default: 8080)");
        options.addOption("d", true, "Demanddemo mode");
        options.addOption("t", true, "Demanddemo toggle time (ms)");
        options.addOption("l", true, "Log level");

        String odlIP = "127.0.0.1";
        String odlUser = "admin";
        String odlPw = "admin";
        String odlPort = "8080";
        boolean demandDemo = false;
        int demandToggleTime = 5000;
        Level logLevel = Level.INFO;

        CommandLineParser parser = new BasicParser();
        try
        {
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("ip")) {
                odlIP = cmd.getOptionValue("ip").toString();
            }
            if(cmd.hasOption("u")) {
                odlUser = cmd.getOptionValue("u").toString();
            }
            if(cmd.hasOption("pw")) {
                odlPw = cmd.getOptionValue("pw").toString();
            }
            if(cmd.hasOption("p")) {
                odlPort = cmd.getOptionValue("p").toString();
            }
            if(cmd.hasOption("d")) {
                if(cmd.getOptionValue("d").toString().toLowerCase().contains("true"))
                    demandDemo = true;
                else
                    demandDemo = false;
            }
            if(cmd.hasOption("t")) {
                demandToggleTime = Integer.parseInt(cmd.getOptionValue("t").toString());
            }
            if(cmd.hasOption("l")){
                switch(cmd.getOptionValue("l").toString().toLowerCase()){
                    case "all":
                        logLevel = Level.ALL;
                        break;

                    case "debug":
                        logLevel = Level.DEBUG;
                        break;

                    case "error":
                        logLevel = Level.ERROR;
                        break;

                    case "fatal":
                        logLevel = Level.FATAL;
                        break;

                    case "off":
                        logLevel = Level.OFF;
                        break;

                    case "trace":
                        logLevel = Level.TRACE;
                        break;

                    case "warn":
                        logLevel = Level.WARN;
                        break;

                    default:
                        logLevel = Level.INFO;
                        break;
                }
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration conf = ctx.getConfiguration();
        conf.getLoggerConfig("SDNMiddlewareLogger").setLevel(logLevel);
        ctx.updateLoggers(conf);

        OdlSettings settings = new OdlSettings(odlIP, odlUser, odlPw, odlPort, demandDemo, demandToggleTime);

        logger.info("Log level: " + logLevel.toString());
        logger.info("Using controller @ " + odlIP);
        logger.info("With port port " + odlPort);
        logger.info("Using Opendaylight user '" + odlUser + "'");
        logger.info("With password '" + odlPw + "'");
        logger.info("Demand demo mode: " + demandDemo);
        if (demandDemo)
            logger.info("Demand toggle time: " + demandToggleTime);

        if(demandDemo){
            Thread thread = new Thread(new DemandSimulator(settings));
            thread.start();
        }

        return settings;
    }
}
