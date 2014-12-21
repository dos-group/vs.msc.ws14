package de.tuberlin.cit.sdn.opendaylight.model.flow;

import de.tuberlin.cit.sdn.opendaylight.model.node.Node;

import java.util.List;

public class FlowConfig {
    public boolean installInHw;
    public String name;
    public Node node;
    public Integer ingressPort;
    public Integer priority;
    public String etherType;
    public String nwSrc;
    public List<String> actions;
}
