package de.tuberlin.cit.sdn.opendaylight;

import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import org.apache.commons.cli.*;

/**
 * Created by Nico on 02.01.2015.
 *
 * Contains various helper methods.
 */
public class Utils {

    private static Utils _instance = null;

    public static Utils GetInstance(){
        if(_instance == null)
            _instance = new Utils();
        return _instance;
    }

    private Utils() {
    }

    public OdlSettings ReadSettings(String[] args) {
        Options options = new Options();
        options.addOption("ip", true, "IP address of controller. Default: 127.0.0.1");
        options.addOption("u", true, "Opendaylight username (default: 'admin')");
        options.addOption("pw", true, "Opendaylight password (default: 'admin')");
        options.addOption("p", true, "Opendaylight port (default: 8080)");

        String odlIP = "127.0.0.1";
        String odlUser = "admin";
        String odlPw = "admin";
        String odlPort = "8080";

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
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        OdlSettings _settings = new OdlSettings(odlIP, odlUser, odlPw, odlPort);

        System.out.println("Using controller @ " + odlIP);
        System.out.println("With port port " + odlPort);
        System.out.println("Using Opendaylight user '" + odlUser + "'");
        System.out.println("With password '" + odlPw + "'");

        return _settings;
    }
}
