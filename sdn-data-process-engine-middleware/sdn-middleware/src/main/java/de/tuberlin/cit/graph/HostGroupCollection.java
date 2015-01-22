package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.*;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class HostGroupCollection {

    private Set<HostGroup> hostGroups;
    private Set<Host> hosts;

    private List<NetworkDevice> networkDevices;

    private long[][] networkDevicesDistances;

    private HostGroupCollection() {
    }

    /**
     * Standard constructor to create a host group collection.
     *
     * @param graph Network graph.
     */
    public HostGroupCollection(DirectedSparseMultigraph graph) {
        Collection<NetworkVertex> nodes = graph.getVertices();
        Collection<NetworkEdge> connections = graph.getEdges();

        this.hostGroups = new HashSet<>();
        this.hosts = new HashSet<>();
        this.networkDevices = new ArrayList<>();

        fillWithData(nodes, connections);
        calculateNetworkDevicesDistances(graph);
    }

    private void fillWithData(Collection<NetworkVertex> nodes, Collection<NetworkEdge> connections) {
        // add all hosts and host groups
        HostGroup hostGroup;
        Host host;
        NetworkDevice networkDevice;
        for (NetworkVertex node : nodes) { // create a group only if it is a network device (e.g. switch)
            if (node instanceof NetworkDevice) {
                networkDevice = (NetworkDevice) node;
                hostGroup = new HostGroup(networkDevice);
                this.networkDevices.add(networkDevice);
                for (NetworkEdge connection : connections) { // go through all connections
                    if (connection.getHeadVertex().equals(networkDevice)) { // find connected nodes
                        NetworkVertex tail = connection.getTailVertex();
                        if (tail instanceof Host) { // add host to the group
                            host = (Host) tail;
                            hostGroup.addHost(host);
                            this.hosts.add(host);
                        }
                    }
                }
                this.hostGroups.add(hostGroup);
            }
        }
    }

    /**
     * @return Next best <code>Host</code>.
     */
    public synchronized Host findNextBestHost() {
        Host nextHost = null;
        if (this.getFreeHosts().size() < 1) { // no free hosts
            nextHost = null;
        } else {
            throw new NotImplementedException();
        }

        if (nextHost != null) { // block the host
            nextHost.setFree(false);
        }
        return nextHost;
    }

    /**
     * @param hostId Id of the Host to unblock.
     */
    public synchronized void releaseHost(String hostId) {
        Host hostToRelease = null;
        for (Host host : this.hosts) {
            if (host.getId().equals(hostId)) {
                hostToRelease = host;
                break;
            }
        }
        if (hostToRelease != null) { // unblock host
            hostToRelease.setFree(true);
        }
    }

    private Map<HostGroup, Integer> currentOccupation() {
        Map<HostGroup, Integer> currentOccupation = new HashMap<>();
        int freeHosts;
        for (HostGroup hostGroup : this.hostGroups) {
            freeHosts = hostGroup.numberOfFreeHosts();
            currentOccupation.put(hostGroup, freeHosts);
        }
        return currentOccupation;
    }

    public Set<Host> getFreeHosts() {
        Set<Host> freeHosts = new HashSet<>();
        for (Host host : this.hosts) {
            if (host.isFree()) {
                freeHosts.add(host);
            }
        }
        return freeHosts;
    }

    private void calculateNetworkDevicesDistances(DirectedSparseMultigraph graph) {
        DistanceCalculator calculator = new DistanceCalculator(graph);
        this.networkDevicesDistances = calculator.calculateDistanceMatrix(networkDevices);
    }

    public Set<HostGroup> getHostGroups() {
        return hostGroups;
    }

    public Set<Host> getHosts() {
        return hosts;
    }

    public List<NetworkDevice> getNetworkDevices() {
        return networkDevices;
    }

    public long[][] getNetworkDevicesDistances() {
        return networkDevicesDistances;
    }
}
