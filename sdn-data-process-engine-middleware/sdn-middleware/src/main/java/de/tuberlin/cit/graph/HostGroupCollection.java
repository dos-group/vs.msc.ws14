package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class HostGroupCollection {

    private Set<HostGroup> hostGroups;
    private Set<Host> hosts;
    private long[][] distanceMatrix;

    private HostGroupCollection() {
    }

    /**
     * Standard constructor to create a host group collection.
     *
     * @param nodes          Set of network vertices.
     * @param connections    Set of network edges.
     * @param distanceMatrix Shortest distances between the vertices.
     */
    public HostGroupCollection(List<NetworkVertex> nodes, List<NetworkEdge> connections, long[][] distanceMatrix) {
        this.hostGroups = new HashSet<>();
        this.hosts = new HashSet<>();
        this.distanceMatrix = distanceMatrix;

        // add all hosts and host groups
        HostGroup hostGroup;
        Host host;
        NetworkDevice networkDevice;
        for (NetworkVertex node : nodes) { // create a group only if it is a network device (e.g. switch)
            if (node instanceof NetworkDevice) {
                networkDevice = (NetworkDevice) node;
                hostGroup = new HostGroup(networkDevice);
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
     * @param numberOfHostsForJob Number of hosts that are needed for job.
     * @return Set of hosts which can be used for the job. Returns <code>null</code> if not enough free nodes.
     */
    public synchronized Set<Host> getBestHosts(int numberOfHostsForJob) {
        Set<Host> hosts = null;
        int freeHosts = numberOfFreeHosts();
        if (numberOfHostsForJob < freeHosts) { // not enough free hosts > should wait (queue)
            hosts = null;
        } else if (numberOfHostsForJob == freeHosts) { // just enough free nodes
            hosts = getFreeHosts();
        } else { // find them :)
            hosts = findBestHosts(numberOfHostsForJob);
        }
        return hosts;
    }

    private Set<Host> findBestHosts(int n) {
        Set<Host> hosts = null;
        Map<HostGroup, Integer> currentOccupation = currentOccupation();
        HostGroup perfectHostGroup = findPerfectGroupForNHosts(currentOccupation, n);
        if (perfectHostGroup != null) { // PERFECT solution found (perfect fit)
            hosts = perfectHostGroup.getHosts();
        } else {
            HostGroup suitableHostGroup = findSuitableGroupForNHosts(currentOccupation, n);
            if (suitableHostGroup != null) { // SUBOPTIMAL solution found (host group is bigger)
                hosts = suitableHostGroup.occupyNHosts(n);
            } else { // DISTRIBUTED set of hosts (hosts are not in the same host group)
                hosts = findDistributedSolution(n);
            }
        }
        return hosts;
    }

    private Set<Host> findDistributedSolution(int n) {
        throw new NotImplementedException();
    }

    /**
     * @return Free <code>HostGroup</code> of the size <code>numberOfHostsNeeded</code>.
     */
    private HostGroup findPerfectGroupForNHosts(Map<HostGroup, Integer> currentOccupation, int numberOfHostsNeeded) {
        for (Map.Entry<HostGroup, Integer> entry : currentOccupation.entrySet()) {
            if (entry.getValue() == numberOfHostsNeeded) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @return <code>HostGroup</code> with a size > <code>numberOfHostsNeeded</code> (suboptimal for successor jobs).
     */
    private HostGroup findSuitableGroupForNHosts(Map<HostGroup, Integer> currentOccupation, int numberOfHostsNeeded) {
        for (Map.Entry<HostGroup, Integer> entry : currentOccupation.entrySet()) {
            if (entry.getValue() > numberOfHostsNeeded) {
                return entry.getKey();
            }
        }
        return null;
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

    public int numberOfFreeHosts() {
        return getFreeHosts().size();
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
}
