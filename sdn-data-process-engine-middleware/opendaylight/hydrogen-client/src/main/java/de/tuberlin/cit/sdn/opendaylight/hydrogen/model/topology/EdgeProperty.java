package de.tuberlin.cit.sdn.opendaylight.hydrogen.model.topology;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.cit.sdn.opendaylight.hydrogen.model.Property;

import java.util.Map;

public class EdgeProperty {
    public Edge edge;
    public Map<String, Property> properties;

    @JsonIgnore
    public long getBandwidth() {
        Property bandwidth = properties.get("bandwidth");
        return bandwidth != null ? Long.parseLong(bandwidth.value) : 0;
    }
}
