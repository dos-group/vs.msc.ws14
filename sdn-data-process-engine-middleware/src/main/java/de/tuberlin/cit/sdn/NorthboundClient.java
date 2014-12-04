package de.tuberlin.cit.sdn;

public interface NorthboundClient {

    void getTopology();

    void getStatistics();

    void getFlows();

    void setFlows();
}
