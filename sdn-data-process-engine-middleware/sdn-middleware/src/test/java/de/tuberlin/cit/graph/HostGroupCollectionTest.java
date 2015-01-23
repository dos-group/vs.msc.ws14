package de.tuberlin.cit.graph;

import de.tuberlin.cit.graph.model.Host;
import de.tuberlin.cit.graph.model.NetworkDevice;
import de.tuberlin.cit.graph.model.NetworkEdge;
import de.tuberlin.cit.graph.model.NetworkVertex;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HostGroupCollectionTest {

    DirectedSparseMultigraph<NetworkVertex, NetworkEdge> graph = new DirectedSparseMultigraph();

    @Before
    public void prepareGraph() {
        NetworkDevice s1 = new NetworkDevice("s1");
        NetworkDevice s2 = new NetworkDevice("s2");
        NetworkDevice s3 = new NetworkDevice("s3");
        NetworkDevice s4 = new NetworkDevice("s4");

        Host h1 = new Host("h1");
        Host h2 = new Host("h2");
        Host h3 = new Host("h3");
        Host h4 = new Host("h4");
        Host h5 = new Host("h5");
        Host h6 = new Host("h6");
        Host h7 = new Host("h7");
        Host h8 = new Host("h8");

        addNetworkEdge(h1, s1);
        addNetworkEdge(h2, s1);
        addNetworkEdge(h3, s1);

        addNetworkEdge(h4, s3);
        addNetworkEdge(h5, s3);

        addNetworkEdge(h6, s2);
        addNetworkEdge(h7, s2);

        addNetworkEdge(h8, s4);

        addNetworkEdge(s2, s1, 10000);
        addNetworkEdge(s1, s2, 10000);

        addNetworkEdge(s4, s1, 5000);
        addNetworkEdge(s1, s4, 5000);

        addNetworkEdge(s3, s1);
        addNetworkEdge(s1, s3);

        addNetworkEdge(s3, s4);
        addNetworkEdge(s4, s3);
    }

    private NetworkEdge addNetworkEdge(NetworkVertex tail, NetworkVertex head) {
        NetworkEdge edge = new NetworkEdge();
        edge.setTail(tail, 0);
        edge.setHead(head, 0);

        graph.addEdge(edge, edge.getTailVertex(), edge.getHeadVertex(), EdgeType.DIRECTED);
        return edge;
    }

    private NetworkEdge addNetworkEdge(NetworkVertex tail, NetworkVertex head, long bandwidth) {
        NetworkEdge edge = addNetworkEdge(tail, head);
        edge.setBandwidth(bandwidth);
        return edge;
    }

    HostGroupCollection hostGroupCollection;

    @Test
    public void constructor_Creation_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        assertEquals(8, hostGroupCollection.getHosts().size());
        assertEquals(4, hostGroupCollection.getHostGroups().size());
        assertEquals(4, hostGroupCollection.getNetworkDevices().size());
        assertEquals(4, hostGroupCollection.getNetworkDevicesDistances().length);
    }

    @Test
    public void findNextBestHost_WhenAllHostsAreFree_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        Host nextBestHost = hostGroupCollection.findNextBestHost();
        // should be in the biggest host group
        assertTrue(ArrayUtils.contains(new String[]{"h1", "h2", "h3"}, nextBestHost.getId()));
    }

    @Test
    public void findNextBestHost_WhenAllHostsAreNotFree_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        for (Host host : hostGroupCollection.getHosts()) {
            host.setFree(false);
        }
        // should return "null" if all the hosts in use
        assertNull(hostGroupCollection.findNextBestHost());
    }

    @Test
    public void findNextBestHost_WhenOneHostInTheSameGroupIsBusy_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        // set host2 in use
        for (Host host : hostGroupCollection.getHosts()) {
            if ("h2".equals(host.getId())) {
                host.setFree(false);
                break;
            }
        }
        Host nextBestHost = hostGroupCollection.findNextBestHost();
        // should be in the biggest host group
        assertTrue(ArrayUtils.contains(new String[]{"h1", "h3"}, nextBestHost.getId()));
    }

    @Test
    public void findNextBestHost_WhenAllHostsInOneGroupAreBusy_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        // set host 1,2,3 use
        for (Host host : hostGroupCollection.getHosts()) {
            if ("h1".equals(host.getId()) || "h2".equals(host.getId()) || "h3".equals(host.getId()) ) {
                host.setFree(false);
            }
        }
        Host nextBestHost = hostGroupCollection.findNextBestHost();
        // should be in the closest group
        assertTrue(nextBestHost.getId(), ArrayUtils.contains(new String[]{"h6", "h7"}, nextBestHost.getId()));
    }

    @Test
    public void findNextBestHost_WhenAllHostsInOneGroupAreBusyAndTheClosestGroupIsBusy_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        // set host 1,2,3,5,6,7 in use
        for (Host host : hostGroupCollection.getHosts()) {
            if ("h1".equals(host.getId()) || "h2".equals(host.getId()) || "h3".equals(host.getId()) || "h5".equals(host.getId())
                    || "h6".equals(host.getId()) || "h7".equals(host.getId())) {
                host.setFree(false);
            }
        }
        Host nextBestHost = hostGroupCollection.findNextBestHost();
        // should be in the closest group which has some free nodes
        assertEquals("h4", nextBestHost.getId());
    }

    @Test
    public void findNextBestHost_EmptyGraph_Test() {
        hostGroupCollection = new HostGroupCollection(new DirectedSparseMultigraph());
        assertNull(hostGroupCollection.findNextBestHost());
    }

    @Test
    public void findNextBestHost_NoFreeHosts_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        for (Host host : hostGroupCollection.getHosts()) {
            host.setFree(false);
        }
        assertNull(hostGroupCollection.findNextBestHost());
    }

    @Test
    public void getFreeHosts_AllFree_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        assertEquals(8, hostGroupCollection.getFreeHosts().size());
    }

    @Test
    public void getFreeHosts_OneNotFree_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        for (Host host : hostGroupCollection.getHosts()) {
            if ("h2".equals(host.getId())) {
                host.setFree(false);
                break;
            }
        }
        assertEquals(7, hostGroupCollection.getFreeHosts().size());
    }

    @Test
    public void getFreeHosts_AllNotFree_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        for (Host host : hostGroupCollection.getHosts()) {
            host.setFree(false);
        }
        assertEquals(0, hostGroupCollection.getFreeHosts().size());
    }
}
