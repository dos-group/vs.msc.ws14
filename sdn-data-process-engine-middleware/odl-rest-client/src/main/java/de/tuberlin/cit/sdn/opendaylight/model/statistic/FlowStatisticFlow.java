package de.tuberlin.cit.sdn.opendaylight.model.statistic;

import java.util.List;

public class FlowStatisticFlow {
    public FlowStatisticMatch match;
    public List<FlowStatisticAction> actions;
    public int priority;
    public int idleTimeout;
    public int hardTimeout;
    public int id;
}
