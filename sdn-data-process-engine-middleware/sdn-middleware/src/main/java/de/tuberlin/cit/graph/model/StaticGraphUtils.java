package de.tuberlin.cit.graph.model;


import org.apache.commons.collections15.Transformer;

public class StaticGraphUtils {

    public static Transformer<NetworkEdge, Long> createTransporter(final long sizeOfDataToTransport) {
        return new Transformer<NetworkEdge, Long>() {
            public Long transform(NetworkEdge link) {
                return link.calculateWeight(sizeOfDataToTransport);
            }
        };
    }
}
