package de.tuberlin.cit.sdn.middleware.graph;

import java.util.HashMap;
import java.util.Map;

public class DistanceMatrix {
    Map<String, Map<String, Long>> matrix = new HashMap<>();

    public long getDistance(String from, String to) {
        Map<String, Long> map = matrix.get(from);
        if (map != null) {
            Long distance = map.get(to);
            return distance != null ? distance : 0;
        } else {
            return 0;
        }
    }

    public void putDistance(String from, String to, Number distance) {
        if (distance != null) {
            putDistance(from, to, distance.longValue());
        }
    }

    public void putDistance(String from, String to, long distance) {
        Map<String, Long> map = matrix.get(from);
        if (map == null) {
            map = new HashMap<>();
            matrix.put(from, map);
        }
        map.put(to, distance);
    }
}
