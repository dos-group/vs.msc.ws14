package de.tuberlin.cit.graph;


import de.tuberlin.cit.graph.model.NetworkEdge;
import org.apache.commons.collections15.Transformer;

public class StaticGraphUtils {

    public static Transformer<NetworkEdge, Long> createTransporter(final long sizeOfDataToTransport) {
        return new Transformer<NetworkEdge, Long>() {
            public Long transform(NetworkEdge link) {
                return link.calculateWeight(sizeOfDataToTransport);
            }
        };
    }

    public static Transformer<NetworkEdge, Long> createSimpleTransporter() {
        return new Transformer<NetworkEdge, Long>() {
            public Long transform(NetworkEdge link) {
                return link.calculateWeight();
            }
        };
    }
}
