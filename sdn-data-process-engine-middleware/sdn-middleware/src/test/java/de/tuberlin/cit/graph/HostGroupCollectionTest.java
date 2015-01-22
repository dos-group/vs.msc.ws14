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

        NetworkEdge edge1 = new NetworkEdge();
        edge1.setHead(s1, 0);
        edge1.setTail(h1, 0);
        NetworkEdge edge2 = new NetworkEdge();
        edge2.setHead(s1, 0);
        edge2.setTail(h2, 0);
        NetworkEdge edge3 = new NetworkEdge();
        edge3.setHead(s1, 0);
        edge3.setTail(h3, 0);
        NetworkEdge edge4 = new NetworkEdge();
        edge4.setHead(s3, 0);
        edge4.setTail(h4, 0);
        NetworkEdge edge5 = new NetworkEdge();
        edge5.setHead(s3, 0);
        edge5.setTail(h5, 0);
        NetworkEdge edge6 = new NetworkEdge();
        edge6.setHead(s2, 0);
        edge6.setTail(h6, 0);
        NetworkEdge edge7 = new NetworkEdge();
        edge7.setHead(s2, 0);
        edge7.setTail(h7, 0);
        NetworkEdge edge8 = new NetworkEdge();
        edge8.setHead(s4, 0);
        edge8.setTail(h8, 0);
        NetworkEdge edge9 = new NetworkEdge();
        edge9.setHead(s1, 0);
        edge9.setTail(h5, 0);

        NetworkEdge edge10 = new NetworkEdge();
        edge10.setHead(s2, 0);
        edge10.setTail(h1, 0);
        edge10.setBandwidth(10000);
        NetworkEdge edge10b = new NetworkEdge();
        edge10b.setHead(h1, 0);
        edge10b.setTail(s2, 0);
        edge10b.setBandwidth(10000);

        NetworkEdge edge11 = new NetworkEdge();
        edge11.setHead(s1, 0);
        edge11.setTail(s4, 0);
        edge11.setBandwidth(5000);
        NetworkEdge edge11b = new NetworkEdge();
        edge11b.setHead(s4, 0);
        edge11b.setTail(s1, 0);
        edge11b.setBandwidth(5000);

        NetworkEdge edge12 = new NetworkEdge();
        edge12.setHead(s1, 0);
        edge12.setTail(s3, 0);
        NetworkEdge edge12b = new NetworkEdge();
        edge12b.setHead(s3, 0);
        edge12b.setTail(s1, 0);


        NetworkEdge edge13 = new NetworkEdge();
        edge13.setHead(s3, 0);
        edge13.setTail(s4, 0);
        NetworkEdge edge13b = new NetworkEdge();
        edge13b.setHead(s4, 0);
        edge13b.setTail(s3, 0);

        graph.addEdge(edge1, s1, h1, EdgeType.DIRECTED);
        graph.addEdge(edge2, s1, h2, EdgeType.DIRECTED);
        graph.addEdge(edge3, s1, h3, EdgeType.DIRECTED);
        graph.addEdge(edge4, s3, h4, EdgeType.DIRECTED);
        graph.addEdge(edge5, s3, h5, EdgeType.DIRECTED);
        graph.addEdge(edge6, s2, h6, EdgeType.DIRECTED);
        graph.addEdge(edge7, s2, h7, EdgeType.DIRECTED);
        graph.addEdge(edge8, s4, h8, EdgeType.DIRECTED);
        graph.addEdge(edge9, s1, h5, EdgeType.DIRECTED);
        graph.addEdge(edge10, s2, s1, EdgeType.DIRECTED);
        graph.addEdge(edge10b, s1, s2, EdgeType.DIRECTED);
        graph.addEdge(edge11, s1, s4, EdgeType.DIRECTED);
        graph.addEdge(edge11b, s4, s1, EdgeType.DIRECTED);
        graph.addEdge(edge12, s1, s3, EdgeType.DIRECTED);
        graph.addEdge(edge12b, s3, s1, EdgeType.DIRECTED);
        graph.addEdge(edge13, s3, s4, EdgeType.DIRECTED);
        graph.addEdge(edge13b, s4, s3, EdgeType.DIRECTED);
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
        assertTrue(ArrayUtils.contains(new String[]{"h1", "h3", "h5"}, nextBestHost.getId()));
    }

    @Test
    public void findNextBestHost_WhenAllHostsInOneGroupAreBusy_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        // set host 1,2,3 and 5 in use
        for (Host host : hostGroupCollection.getHosts()) {
            if ("h1".equals(host.getId()) || "h2".equals(host.getId()) || "h3".equals(host.getId()) || "h5".equals(host.getId())) {
                host.setFree(false);
            }
        }
        Host nextBestHost = hostGroupCollection.findNextBestHost();
        // should be in the closest group
        assertTrue(ArrayUtils.contains(new String[]{"h6", "h7"}, nextBestHost.getId()));
    }

    @Test
    public void findNextBestHost_WhenAllHostsInOneGroupAreBusyAndTheClosestGroupIsBusy_Test() {
        hostGroupCollection = new HostGroupCollection(graph);
        // set host 1,2,3 and 5 in use
        for (Host host : hostGroupCollection.getHosts()) {
            if ("h1".equals(host.getId()) || "h2".equals(host.getId()) || "h3".equals(host.getId()) || "h5".equals(host.getId())
                    || "h6".equals(host.getId()) || "h7".equals(host.getId())) {
                host.setFree(false);
            }
        }
        Host nextBestHost = hostGroupCollection.findNextBestHost();
        // should be in the closest group which has some free nodes
        assertEquals("h8", nextBestHost.getId());
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
