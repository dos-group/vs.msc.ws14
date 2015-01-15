package de.tuberlin.cit.graph.model;

public class NetworkEdge extends GraphElement {

    private NetworkVertex tailVertex;
    private NetworkVertex headVertex;
    private int hostPort = 0;
    private long latency = 0;
    private long bandwidth = 0;

    public long getUsedBandwidth() {
        return usedBandwidth;
    }

    public void setUsedBandwidth(long usedBandwidth) {
        this.usedBandwidth = usedBandwidth;
    }

    private long usedBandwidth = 0;

    public NetworkVertex getTailVertex() {
        return tailVertex;
    }

    public void setTailVertex(NetworkVertex tailVertex) {
        this.tailVertex = tailVertex;
    }

    public NetworkVertex getHeadVertex() {
        return headVertex;
    }

    public void setHeadVertex(NetworkVertex headVertex) {
        this.headVertex = headVertex;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long calculateWeight(long dataSize){
        return (dataSize * (bandwidth - usedBandwidth) + latency);
    }

    public NetworkEdge(String id) {
        super(id);
    }
}
