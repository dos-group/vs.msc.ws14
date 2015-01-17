package de.tuberlin.cit.sdn.opendaylight.model.flow;

import de.tuberlin.cit.sdn.opendaylight.model.node.Node;

import java.util.List;

public class FlowConfig {
    public String name;
    public Node node;
    public Integer ingressPort;
    public Integer priority;
    // when the entry should be removed regardless of activity
    public Integer hardTimeout;
    // when the entry should be removed due to a lack of activity
    public Integer idleTimeout;
    public String cookie;

    // Layer 2
    public String etherType;
    public Integer vlanId;
    public Integer vlanPriority;
    public String dlSrc;
    public String dlDst;

    // Layer 3
    public String nwSrc;
    public String nwDst;
    public Integer tosBits;

    // Layer 4
    public Integer tpSrc;
    public Integer tpDst;
    public String protocol;

    public boolean installInHw;
    public List<String> actions;
}
