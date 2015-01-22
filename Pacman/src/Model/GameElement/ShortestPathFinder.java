package Model.GameElement;

import Model.Cell.Guider;
import Model.Cell.Node;
import Model.Cell.Path;
import Model.Cell.PathGuide;
import Model.Direction;
import Model.PlayGround;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShortestPathFinder extends PathFinder {

    private final List<Node> nodes;
    private final List<Path> paths;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;

    public ShortestPathFinder(PathingTarget target, PlayGround playGround) {
        super(target);
        this.nodes = new ArrayList(playGround.getNodes());
        this.paths = new ArrayList(playGround.getPaths());
    }

    public void execute(Node source) {
        settledNodes = new HashSet();
        unSettledNodes = new HashSet();
        distance = new HashMap();
        predecessors = new HashMap();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Node node, Node target) {
        for (Path path : paths) {
            if (path.getStartNode().equals(node)
                    && path.getEndNode().equals(target)) {
                return path.getLength();
            } else if (path.getEndNode().equals(node) && path.getStartNode().equals(target)) {
                return path.getLength();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Path path : paths) {
            if (path.getStartNode().equals(node) && !isSettled(path.getEndNode())) {
                neighbors.add(path.getEndNode());
            } else if (path.getEndNode().equals(node) && !isSettled(path.getStartNode())) {
                neighbors.add(path.getStartNode());
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> nodees) {
        Node minimum = null;
        for (Node node : nodees) {
            if (minimum == null) {
                minimum = node;
            } else {
                if (getShortestDistance(node) < getShortestDistance(minimum)) {
                    minimum = node;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    private PathingWrapper getPath(Node target) {
        PathingWrapper pathingWrapper = new PathingWrapper();
        Node step = target;
        // check if a path exists

        pathingWrapper.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            pathingWrapper.add(step);
        }
        // Put it into the correct order
        pathingWrapper.reverse();
        return pathingWrapper;
    }

    @Override
    protected Direction getMove(Guider guider) {
        boolean onSamePath = false;
        int samePathDist = Integer.MAX_VALUE;
        Direction samePathDir = null;

        if (guider.getClosestNodes().size() == 1 && getTarget().getGuider().getClosestNodes().size() == 2) {
            Node node = guider.getClosestNodes().get(0);
            for (Path path : node.getPaths()) {
                if (((PathGuide) getTarget().getGuider()).onSamePath(new PathGuide(path, null))) {
                    onSamePath = true;

                    int tempDist = ((PathGuide) getTarget().getGuider()).distanceToNode(node);
                    if (tempDist < samePathDist) {
                        samePathDist = tempDist;
                        if (path.isEndNode(node)) {
                            samePathDir = node.getDirectionOfNode(path.getStartNode());
                        } else {
                            samePathDir = node.getDirectionOfNode(path.getEndNode());
                        }
                    }
                }
            }
        }

        if (guider.getClosestNodes().size() == 2 && getTarget().getGuider().getClosestNodes().size() == 1) {
            Node node = getTarget().getGuider().getClosestNodes().get(0);
            for (Path path : node.getPaths()) {
                if (((PathGuide) guider).onSamePath(new PathGuide(path, null))) {
                    onSamePath = true;
                    int tempDist = ((PathGuide) guider).distanceToNode(node);
                    if (tempDist < samePathDist) {
                        samePathDist = tempDist;
                        samePathDir = ((PathGuide) guider).getDirectionOfNode(node);
                    }
                }
            }
        }

        if (guider.getClosestNodes().size() == 2) {
            if (getTarget().getGuider().getClosestNodes().size() == 2) {
                if (((PathGuide) guider).onSamePath((PathGuide) getTarget().getGuider())) {
                    onSamePath = true;
                    Node node = guider.getClosestNodes().get(0);
                    int tempDist = ((PathGuide) guider).distanceToNode(node) - ((PathGuide) getTarget().getGuider()).distanceToNode(node);
                    if (tempDist < Math.abs(samePathDist)) {
                        if (samePathDist > 0) {
                            samePathDir = ((PathGuide) guider).getDirectionOfNode(node);
                        } else {
                            samePathDir = ((PathGuide) guider).getDirectionOfNode(node).inverse();
                        }
                    }
                }
            }
        }

        Map<Node, Integer> distancesToStartNode = new HashMap();
        Map<Node, Integer> distancesToEndNodes = new HashMap();

        List<Node> startNodes = guider.getClosestNodes();
        for (Node node : startNodes) {
            distancesToStartNode.put(node, guider.distanceToNode(node));
        }
        List<Node> endNodes = getTarget().getGuider().getClosestNodes();

        for (Node node : endNodes) {
            distancesToEndNodes.put(node, getTarget().getGuider().distanceToNode(node));
        }
        ArrayList<PathingWrapper> possPaths = new ArrayList();
        PathingWrapper bestPath = null;
        int minimum = Integer.MAX_VALUE;
        int totalDist = Integer.MAX_VALUE;
        for (Node start : startNodes) {
            execute(start);

            for (Node end : endNodes) {
                PathingWrapper curPath = getPath(end);
                if (curPath != null) {
                    totalDist = curPath.getDistance()
                            + distancesToStartNode.get(curPath.getNodes().get(0))
                            + distancesToEndNodes.get(curPath.getNodes().get(curPath.getNodes().size() - 1));
                    if (minimum > totalDist) {
                        minimum = totalDist;
                        bestPath = curPath;
                    }
                }
            }
        }

        if (onSamePath) {
            if (bestPath != null) {
                if (totalDist >= Math.abs(samePathDist)) {
                   return samePathDir;
                }
            } else {
                return samePathDir;
            }
        }
        
        Node targetNode;
        if (bestPath.getNodes().get(0) == guider.getCurrentCell()) {
            targetNode = bestPath.getNodes().get(1);
        } else {
            targetNode = bestPath.getNodes().get(0);
        }

        return guider.getDirectionOfNode(targetNode);
    }

    private class PathingWrapper {

        private ArrayList<Node> nodes = new ArrayList();
        private int totalDistance = 0;

        public void add(Node node) {
            nodes.add(node);
            totalDistance += distance.get(node);
        }

        public int getDistance() {
            return totalDistance;
        }

        public ArrayList<Node> getNodes() {
            return nodes;
        }

        private void reverse() {
            Collections.reverse(nodes);
        }
    }
}
