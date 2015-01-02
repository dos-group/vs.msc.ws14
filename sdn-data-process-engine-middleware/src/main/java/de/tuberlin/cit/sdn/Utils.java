package de.tuberlin.cit.sdn;

import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import org.apache.commons.cli.*;

/**
 * Created by Nico on 02.01.2015.
 *
 * Contains various helper methods.
 */
public class Utils {

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

        String odlIP = "127.0.0.1";
        String odlUser = "admin";
        String odlPw = "admin";
        String odlPort = "8080";
        boolean demandDemo = false;
        int demandToggleTime = 5000;

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
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        OdlSettings settings = new OdlSettings(odlIP, odlUser, odlPw, odlPort, demandDemo, demandToggleTime);

        System.out.println("Using controller @ " + odlIP);
        System.out.println("With port port " + odlPort);
        System.out.println("Using Opendaylight user '" + odlUser + "'");
        System.out.println("With password '" + odlPw + "'");
        System.out.println("Demand demo mode: " + demandDemo);
        if (demandDemo)
            System.out.println("Demand toggle time: " + demandToggleTime);

        if(demandDemo){
            Thread thread = new Thread(new DemandSimulator(settings));
            thread.start();
        }

        return settings;
    }
}
