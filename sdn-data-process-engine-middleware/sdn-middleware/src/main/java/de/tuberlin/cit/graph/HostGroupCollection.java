package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.*;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

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
        this.hostGroups = new HashSet<>();
        this.hosts = new HashSet<>();
        this.networkDevices = new ArrayList<>();

        fillWithData(graph);
        calculateNetworkDevicesDistances(graph);
    }

    private void fillWithData(DirectedSparseMultigraph graph) {
        Collection<NetworkVertex> nodes = graph.getVertices();

        // add all hosts and host groups
        HostGroup hostGroup;
        Host host;
        NetworkDevice networkDevice;
        for (NetworkVertex node : nodes) { // create a group only if it is a network device (e.g. switch)
            if (node instanceof NetworkDevice) {
                networkDevice = (NetworkDevice) node;
                hostGroup = new HostGroup(networkDevice);
                this.networkDevices.add(networkDevice);
                Collection<NetworkEdge> connections = graph.getInEdges(networkDevice);
                for (NetworkEdge connection : connections) { // go through ingoing connections
                    NetworkVertex tail = connection.getTailVertex();
                    if (tail instanceof Host) { // add host to the group
                        host = (Host) tail;
                        hostGroup.addHost(host);
                        this.hosts.add(host);
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
        Host nextHost;
        if (this.hosts.size() < 1 || this.getFreeHosts().size() < 1) { // no free hosts
            nextHost = null;
        } else {
            if (this.getFreeHosts().size() == this.hosts.size()) { // all hosts are free
                HostGroup biggestHostGroup = getBiggestHostGroup();
                nextHost = biggestHostGroup.getFreeHost();
            } else {
                nextHost = findFreeHostFromWorkingHostGroup(); // some of working groups have free hosts
                if (nextHost == null) { // all working  host groups are full
                    HostGroup workingHostGroup = findWorkingHostGroup();
                    HostGroup nextHostGroup = findNextHostGroup(workingHostGroup);
                    nextHost = nextHostGroup.getFreeHost();
                }
            }

        }

        if (nextHost != null) { // block the host
            nextHost.setFree(false);
        }
        return nextHost;
    }

    private HostGroup findNextHostGroup(HostGroup workingHostGroup) {
        HostGroup nextHostGroup = null;
        int workingGroupNetworkDeviceIndex = getNetworkDeviceIndex(workingHostGroup);

        long minWeight = Long.MAX_VALUE;
        NetworkDevice networkDeviceOfTheNextHostGroup;
        HostGroup potentialNextHostGroup;
        for (int i = 0; i < this.networkDevicesDistances.length; i++) {
            networkDeviceOfTheNextHostGroup = this.networkDevices.get(i);
            potentialNextHostGroup = getHostGroupByNetworkDevice(networkDeviceOfTheNextHostGroup);
            if (i != workingGroupNetworkDeviceIndex && potentialNextHostGroup.hasFreeHosts()
                    && this.networkDevicesDistances[workingGroupNetworkDeviceIndex][i] < minWeight) {
                nextHostGroup = potentialNextHostGroup;
            }
        }
        return nextHostGroup;
    }

    private HostGroup getHostGroupByNetworkDevice(NetworkDevice networkDeviceOfTheNextHostGroup) {
        HostGroup hostGroup = null;
        for (HostGroup group : this.hostGroups) {
            if (group.getNetworkDevice().getId().equals(networkDeviceOfTheNextHostGroup.getId())) {
                hostGroup = group;
                break;
            }
        }
        return hostGroup;
    }

    private int getNetworkDeviceIndex(HostGroup workingHostGroup) {
        int networkDeviceIndex = -1;
        for (int i = 0; i < this.networkDevices.size(); i++) {
            if (this.networkDevices.get(i).getId().equals(workingHostGroup.getId())) {
                networkDeviceIndex = i;
                break;
            }
        }
        return networkDeviceIndex;
    }

    private HostGroup findWorkingHostGroup() {
        HostGroup workingHostGroup = null;
        for (HostGroup hostGroup : hostGroups) {
            if (hostGroup.isBusy()) {
                workingHostGroup = hostGroup;
                break;
            }
        }
        return workingHostGroup;
    }

    private Host findFreeHostFromWorkingHostGroup() {
        Host freeHost = null;
        for (HostGroup hostGroup : this.hostGroups) {
            if (hostGroup.numberOfFreeHosts() < hostGroup.getHosts().size()) {
                freeHost = hostGroup.getFreeHost();
            }
        }
        return freeHost;
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

    private HostGroup getBiggestHostGroup() {
        HostGroup biggestHostGroup = null;
        int maxGroupSize = Integer.MIN_VALUE;
        for (HostGroup hostGroup : this.hostGroups) {
            if (hostGroup.getHosts().size() > maxGroupSize) {
                biggestHostGroup = hostGroup;
                maxGroupSize = hostGroup.getHosts().size();
            }
        }
        return biggestHostGroup;
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
