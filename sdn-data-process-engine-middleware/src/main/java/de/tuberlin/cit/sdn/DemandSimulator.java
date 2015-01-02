package de.tuberlin.cit.sdn;

import de.tuberlin.cit.sdn.model.Demands;
import de.tuberlin.cit.sdn.model.Hostdemand;
import de.tuberlin.cit.sdn.opendaylight.client.HostTrackerClient;
import de.tuberlin.cit.sdn.opendaylight.model.OdlSettings;
import de.tuberlin.cit.sdn.opendaylight.model.host.HostConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nico on 02.01.2015.
 *
 * simulates host demand
 */
public class DemandSimulator implements Runnable {

    private OdlSettings odlSettings;

    public DemandSimulator(OdlSettings settings) {
        this.odlSettings = settings;
    }

    @Override
    public void run() {

        //initial wait to avoid rest call interference
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostTrackerClient htc = new HostTrackerClient(odlSettings);

        Random randomizer = new Random();

        while (true) {
            List<Hostdemand> randomDemands = new ArrayList<Hostdemand>();

            List<HostConfig> hosts = htc.getActiveHosts().hostConfig;
            if (hosts != null) {
                while (hosts.size() > 1) {
                    HostConfig fromHost = hosts.get(randomizer.nextInt(hosts.size()));
                    hosts.remove(fromHost);

                    Collections.shuffle(hosts);
                    List<HostConfig> toHosts = new ArrayList<HostConfig>();

                    for(int i = 0; i<= randomizer.nextInt(hosts.size()); i++)
                        toHosts.add(hosts.get(i));

                    for (HostConfig hc : toHosts) {
                        hosts.remove(hc);
                    }

                    randomDemands.add(new Hostdemand(fromHost, toHosts));
                }
                Demands.getInstance().stepForward(randomDemands);
                System.out.println("Random demands created");

                for(Hostdemand hd : randomDemands){
                    System.out.println("---New future hostdemand:---");
                    System.out.println("From: " + hd.getOriginHost().networkAddress);
                    for(HostConfig hc : hd.getTargetHosts()){
                        System.out.println("- To: " + hc.networkAddress);
                    }
                }
            }
            try {
                Thread.sleep(odlSettings.getToggleTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
