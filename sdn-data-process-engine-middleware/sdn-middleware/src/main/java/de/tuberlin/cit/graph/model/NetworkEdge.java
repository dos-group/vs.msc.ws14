package de.tuberlin.cit.graph.model;

public class NetworkEdge {

    private NetworkVertex tailVertex;
    private int tailPort;
    private NetworkVertex headVertex;
    private int headPort;
    private long latency = 0;
    private long bandwidth = 1;
    private long usedBandwidth = 0;

    public long getUsedBandwidth() {
        return usedBandwidth;
    }

    public void setUsedBandwidth(long usedBandwidth) {
        this.usedBandwidth = usedBandwidth;
    }

    public NetworkVertex getTailVertex() {
        return tailVertex;
    }

    public void setTailVertex(NetworkVertex tailVertex) {
        this.tailVertex = tailVertex;
    }

    public int getTailPort() {
        return tailPort;
    }

    public void setTailPort(int tailPort) {
        this.tailPort = tailPort;
    }

    public NetworkVertex getHeadVertex() {
        return headVertex;
    }

    public void setHeadVertex(NetworkVertex headVertex) {
        this.headVertex = headVertex;
    }

    public int getHeadPort() {
        return headPort;
    }

    public void setHeadPort(int headPort) {
        this.headPort = headPort;
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

    /**
     * @param dataSize size of the data to transport.
     * @return edge weight based on the time to transport the data set.
     */
    public long calculateWeight(Long dataSize) {
        return (dataSize * (bandwidth - usedBandwidth) + latency);
    }

    /**
     * @return simple weight.
     */
    public long calculateWeight() {
        return bandwidth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NetworkEdge that = (NetworkEdge) o;

        if (!headVertex.equals(that.headVertex)) return false;
        if (!tailVertex.equals(that.tailVertex)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + tailVertex.hashCode();
        result = 31 * result + headVertex.hashCode();
        return result;
    }
}
