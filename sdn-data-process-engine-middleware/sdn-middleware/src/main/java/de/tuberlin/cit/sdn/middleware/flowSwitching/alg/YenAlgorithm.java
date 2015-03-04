package de.tuberlin.cit.sdn.middleware.flowSwitching.alg;

import de.tuberlin.cit.sdn.middleware.flowSwitching.model.Link;
import de.tuberlin.cit.sdn.middleware.flowSwitching.model.Switch;

import java.util.LinkedList;


public class YenAlgorithm {

    public static LinkedList<LinkedList<String>> run(LinkedList<Switch> switches, LinkedList<Link> links, String sourceId, String sinkId, int k) {

        LinkedList<LinkedList<String>> kShortestPaths = new LinkedList<LinkedList<String>>();
        LinkedList<LinkedList<String>> potentialKShortestPaths = new LinkedList<LinkedList<String>>();
        LinkedList<Switch> switchesCopy = new LinkedList<Switch>();
        LinkedList<Link> linksCopy = new LinkedList<Link>();

        // determine shortest path via dijkstra algorithm
        kShortestPaths.add(dijkstra(switches, links, sourceId, sinkId));

        for (int kCurrent=1; kCurrent<=k; kCurrent++) {

            LinkedList<String> previousPath = kShortestPaths.get(kCurrent-1);

            // spur node can be any but the last node of the previous kShortestPath
            for (int i=0; i<previousPath.size(); i++) {

                // get spur node
                String spurNodeId = previousPath.get(i);

                // get root path = path from source to spur node
                LinkedList<String> rootPath = new LinkedList<String>();
                for (int j=0; j<=i; j++) {
                    rootPath.add(previousPath.get(j));
                }

                // remove (from graph) all links that are part of any previous kShortestPath with an identical root path
                for (int l=0; l<links.size(); l++) {
                    int equalityIndex = 0;
                    for (LinkedList<String> path : kShortestPaths) {
                        for (int ind=0; ind<path.size(); ind++) {
                            if (path.get(ind).equals(rootPath.get(ind))) {
                                equalityIndex++;
                            }
                        }
                        if(equalityIndex == path.size()) {
                            for (int ind=1; ind<path.size(); ind++) {
                                if (links.get(l).links(path.get(ind-1)) && links.get(l).links(path.get(ind))) {
                                    linksCopy.set(l, null);
                                    break;
                                    //TODO: do it actually right
                                }
                            }
                        }
                    }
                }

                // remove (from graph) all nodes of the root path but the spur node
                //TODO: fill in the blank

                // calculate spur path = path from spur node to sink
                LinkedList<String> spurPath = dijkstra(switchesCopy, linksCopy, spurNodeId, sinkId);

                // construct and add potential kShortestPath
                LinkedList<String> totalPath = new LinkedList<String>();
                for (int t=0; t<rootPath.size(); t++) {
                    totalPath.add(rootPath.get(t));
                }
                for (int t=1; t<spurPath.size(); t++) {
                    totalPath.add(spurPath.get(t));
                }
                potentialKShortestPaths.add(totalPath);
            }

            if (kShortestPaths.isEmpty()) {
                kShortestPaths.add(previousPath);
            } else {
                //TODO: sort kShortestPaths by size, shortest first
                kShortestPaths.add(potentialKShortestPaths.pop());
            }
        }
        return kShortestPaths;
    }

    private static LinkedList<String> dijkstra(LinkedList<Switch> switches, LinkedList<Link> links, String sourceId, String sinkId) {

        // initialize nodes remaining to be worked on
        // -> predecessors are initialized as null
        // -> distances as -1 unless source node in which case 0
        LinkedList<NodeProperties> remaining = new LinkedList<NodeProperties>();
        for (Switch node : switches) {
            if (node.getId().equals(sourceId)) {
                remaining.add(new NodeProperties(node.getId(), 0));
            } else {
                remaining.add(new NodeProperties(node.getId(), -1));
            }
        }

        //actual algorithm
        while (!remaining.isEmpty()) {

            // retrieve next node to work on (the one with the smallest existing distance)
            int min = 0;
            for (int i=0; i<remaining.size(); i++) {
                if (remaining.get(i).distance > -1 && remaining.get(i).distance < remaining.get(min).distance) {
                    min = i;
                }
            }
            NodeProperties currentNode = remaining.remove(min);

            if (!currentNode.switchId.equals(sinkId)) {
            // case we're not yet at the sink

                // get neighbors of current node
                LinkedList<String> neighborIds = new LinkedList<String>();
                for (Switch potentialNeighbor : switches) {
                    for (Link neighborRelation : links) {
                        if (neighborRelation.links(potentialNeighbor.getId()) && neighborRelation.links(currentNode.switchId)) {
                            // this potential neighbor is actually a neighbor, no need to check more links
                            neighborIds.add(potentialNeighbor.getId());
                            break;
                        }
                    }
                }
                // update neighbors that haven't been finalized yet (those that are still in "remaining"
                for (NodeProperties neighbor : remaining) {
                    for (String neighborId : neighborIds) {
                        if (neighbor.switchId.equals(neighborId)) {
                        // case that makes sure the following works only on actual neighbors
                            int potentialNewDistance = currentNode.distance + 1;
                            if (neighbor.distance < 0 || potentialNewDistance < neighbor.distance) {
                                // update only if first time setting distance value or smaller than previous value
                                neighbor.distance = potentialNewDistance;
                                neighbor.predecessor = currentNode;
                            }
                        }
                    }
                }
            } else {
            // case we're at the sink

                // loop through the predecessors and build the path in reverse
                LinkedList<String> shortestPath = new LinkedList<String>();
                shortestPath.add(currentNode.switchId);
                while (currentNode.predecessor != null) {
                    currentNode = currentNode.predecessor;
                    shortestPath.addFirst(currentNode.switchId);
                }
                return shortestPath;
            }
        }

        // won't be needed in case of correct inputs
        return new LinkedList<String>();
    }

}
