/*
 * 
 */
package Model.GameElement;

import Model.Cell.Guider;
import Model.Cell.Node;
import Model.Cell.Path;
import Model.Cell.PathGuide;
import Model.Cell.TraversableCell;
import Model.Direction;
import Model.PlayGround;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthias
 */
public class ShortestPathFinderA extends PathFinder {

    private final List<Node> nodes;
    private final List<Path> paths;
    private Set<Node> closedSet;
    private Set<Node> openSet;
    private Map<Node, Node> cameFrom;
    private Map<Node, Double> distFromStart;
    private Map<Node, Double> heuristicDistToGoal;
    private boolean ghostOnNode;
    private boolean pacmanOnNode;
    private Node targetNode;
    private TraversableCell lastPacmanLocation;
    private PathingWrapper bestPath;

    public ShortestPathFinderA(PathingTarget target, PlayGround playGround) {
        super(target);
        this.nodes = new ArrayList(playGround.getNodes());
        this.paths = new ArrayList(playGround.getPaths());
    }

    public Node execute(Guider guider) {
        closedSet = new HashSet();
        openSet = new HashSet();
        cameFrom = new HashMap();
        distFromStart = new HashMap();
        heuristicDistToGoal = new HashMap();

        if (guider.getCurrentCell() instanceof Node) {
            ghostOnNode = true;
            Node ghostNode = ((Node) guider.getCurrentCell());
            openSet.add(ghostNode);
            distFromStart.put(ghostNode, 0d);
            heuristicDistToGoal.put(ghostNode, getShortestDist(ghostNode));
        } else {
            getTwoStartNodes(guider);
            ghostOnNode = false;
        }

        if (target.getGuider().getCurrentCell() instanceof Node) {
            pacmanOnNode = true;
            targetNode = (Node) target.getGuider().getCurrentCell();
        } else {
            pacmanOnNode = false;
            List<Node> closestNodes = target.getGuider().getClosestNodes();
            
            targetNode = closestNodes.get(0);
            if(getStraightLine(guider, targetNode) > getStraightLine(guider, closestNodes.get(1))) {
                targetNode = closestNodes.get(1);
            }
        }
        
        while (!openSet.isEmpty()) {
            Node current = getMinimum();
            if (targetNode == current) {
                return current;
            }
            openSet.remove(current);
            closedSet.add(current);
            findNewRoute(current);
        }
        
        return target.getGuider().getClosestNodes().get(0);
    }
    
    private double getStraightLine(Guider guider, Node node) {
        double xDist = Math.abs(node.getPositionX() - guider.getCurrentCell().getPositionX());
        double yDist = Math.abs(node.getPositionY() - guider.getCurrentCell().getPositionY());
        double distSquared = (Math.pow(xDist, 2) + Math.pow(yDist, 2));
        double dist = Math.sqrt(distSquared);
        return dist;
    }

    private void findNewRoute(Node current) {
        for (Node neighbor : getNeighbors(current)) {
            boolean neighborVisited = closedSet.contains(neighbor);
            boolean neighborSeen = openSet.contains(neighbor);

            if (neighborVisited) {
                continue;
            }

            double tentativeDist = distFromStart.get(current) + getDistance(current, neighbor) - 1;

            if (!neighborSeen || tentativeDist < heuristicDistToGoal.get(neighbor)) {
                cameFrom.put(neighbor, current);
                distFromStart.put(neighbor, tentativeDist);
                heuristicDistToGoal.put(neighbor, tentativeDist + getShortestDist(neighbor));
                if (!neighborSeen) {
                    openSet.add(neighbor);
                }
            }
        }
    }

    private Node getMinimum() {
        Node minimum = null;
        for (Node node : openSet) {
            if (minimum == null) {
                minimum = node;
            } else {
                if (heuristicDistToGoal.get(node) < heuristicDistToGoal.get(minimum)) {
                    minimum = node;
                }
            }
        }
        return minimum;
    }

    private void getTwoStartNodes(Guider guider) {
        Node nodeA = guider.getClosestNodes().get(0);
        distFromStart.put(nodeA, (double) guider.distanceToNode(nodeA));
        heuristicDistToGoal.put(nodeA, guider.distanceToNode(nodeA) + getShortestDist(nodeA));
        Node nodeB = guider.getClosestNodes().get(1);
        distFromStart.put(nodeB, (double) guider.distanceToNode(nodeB));
        heuristicDistToGoal.put(nodeB, guider.distanceToNode(nodeB) + getShortestDist(nodeB));

        for (Node neighbor : getNeighbors(nodeA)) {
            if (neighbor != nodeB) {
                openSet.add(neighbor);
                cameFrom.put(neighbor, nodeA);
                double distBetween = distFromStart.get(nodeA) + getDistance(nodeA, neighbor);
                distFromStart.put(neighbor, distBetween);
                heuristicDistToGoal.put(neighbor, distBetween + getShortestDist(neighbor));
            }
        }
        closedSet.add(nodeA);

        for (Node neighbor : getNeighbors(nodeB)) {
            if (neighbor != nodeA) {
                openSet.add(neighbor);
                cameFrom.put(neighbor, nodeB);
                double distBetween = distFromStart.get(nodeB) + getDistance(nodeB, neighbor);
                distFromStart.put(neighbor, distBetween);
                heuristicDistToGoal.put(neighbor, distBetween + getShortestDist(neighbor));
            }
        }
        closedSet.add(nodeB);
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList();
        for (Path path : paths) {
            if (path.getStartNode().equals(node) && !closedSet.contains(path.getEndNode())) {
                neighbors.add(path.getEndNode());
            } else if (path.getEndNode().equals(node) && !closedSet.contains(path.getStartNode())) {
                neighbors.add(path.getStartNode());
            }
        }
        return neighbors;
    }

    private double getShortestDist(Node current) {
        double xDist = Math.abs(current.getPositionX() - target.getGuider().getCurrentCell().getPositionX());
        double yDist = Math.abs(current.getPositionY() - target.getGuider().getCurrentCell().getPositionY());
        double distSquared = (Math.pow(xDist, 2) + Math.pow(yDist, 2));
        double dist = Math.sqrt(distSquared);
        return dist;
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

    @Override
    protected Direction getMove(Guider guider) {
        if(guider instanceof PathGuide && target.getGuider() instanceof PathGuide) {
            if( ((PathGuide) guider).onSamePath(((PathGuide) target.getGuider())) ) {
                return checkPathNonOnNode(guider);
            }
        } else if (guider instanceof PathGuide && target.getGuider() instanceof Node) {
            if( ((Node) target.getGuider()).getPaths().contains(((PathGuide) guider).getPath()) ) {
                return checkPathPacmanOnNode(guider);
            }
        } else if (guider instanceof Node && target.getGuider() instanceof PathGuide) {
            if( ((Node) guider).getPaths().contains(((PathGuide) target.getGuider()).getPath()) ) {
                return checkPathGhostOnNode(guider);
            }
        } else {
            for(Path path : ((Node) target.getGuider()).getPaths()) {
                if( ((Node) guider).getPaths().contains(path) ) {
                    return checkPathBothOnNodes(guider);
                }
            }   
        }
        
        Node endNode = execute(guider);
        bestPath = getPath(endNode);

        Node nextNode;
        if (bestPath.getNodes().get(0) == guider.getCurrentCell()) {
            nextNode = bestPath.getNodes().get(1);
        } else {
            nextNode = bestPath.getNodes().get(0);
        }

        return guider.getDirectionOfNode(nextNode);
    }
    
    private Direction checkPathBothOnNodes(Guider guider) {
        return ((Node) guider).getDirectionOfNode((Node) target.getGuider());
    }
    
    private Direction checkPathNonOnNode(Guider guider) {
        List<Node> closestNodes = guider.getClosestNodes();
        Node nodeA = closestNodes.get(0);
        int distPacmanToNodeA = target.getGuider().distanceToNode(nodeA);
        int distGhostToNodeA = guider.distanceToNode(nodeA);
        Node nodeB = closestNodes.get(0);
        int distPacmanToNodeB = target.getGuider().distanceToNode(nodeB);
        int distGhostToNodeB = guider.distanceToNode(nodeB);
        
        if(distPacmanToNodeA < distGhostToNodeA) {
            return guider.getDirectionOfNode(nodeA);
        } else if(distPacmanToNodeB < distGhostToNodeB) {
            return guider.getDirectionOfNode(nodeB);
        } 
        
        return null;
    }
    
    private Direction checkPathGhostOnNode(Guider guider) {
        Path pacmanPath = null;
        for(Path path : ((Node) guider).getPaths()) {
            if( ((PathGuide) target.getGuider()).getPath() == path) {
                pacmanPath = path;
            }
        }
        return ((Node) guider).getPathDirection(pacmanPath);
    }
    
    private Direction checkPathPacmanOnNode(Guider guider) {
        return ((PathGuide) guider).getDirectionOfNode(((Node) target.getGuider()));
    }
    
    public PathingWrapper getPath(Node targetNode) {
        PathingWrapper pathingWrapper = new PathingWrapper();
        Node step = targetNode;
        // check if a path exists

        pathingWrapper.add(step);
        while (cameFrom.get(step) != null) {
            step = cameFrom.get(step);
            pathingWrapper.add(step);
        }
        // Put it into the correct order
        pathingWrapper.reverse();
        return pathingWrapper;
    }
    
    private class PathingWrapper {

        private ArrayList<Node> nodes = new ArrayList();
        private int totalDistance = 0;

        public void add(Node node) {
            nodes.add(node);
            //totalDistance += distance.get(node);
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