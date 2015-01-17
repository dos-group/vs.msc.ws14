package de.tuberlin.cit.sdn.opendaylight.model.statistic;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FlowStatistic {
    public FlowStatisticFlow flow;
    public int tableId;

    // time the flow has been installed in the switch
    public int durationSeconds;
    public int durationNanoseconds;

    public int packetCount;
    public int byteCount;

    @JsonIgnore
    public double getBytesPerSecond() {
        return byteCount / durationSeconds;
    }

    @JsonIgnore
    public double getPacketsPerSecond() {
        return packetCount / durationSeconds;
    }
}
