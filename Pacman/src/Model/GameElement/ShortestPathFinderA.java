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
    private Map<Node, Integer> distFromStart;
    private Map<Node, Integer> heuristicDistToGoal;
    private boolean ghostOnNode;
    private boolean pacmanOnNode;
    private Set<Node> targetNodes;

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
        targetNodes = new HashSet();
        heuristicDistToGoal = new HashMap();

        if (guider.getCurrentCell() instanceof Node) {
            ghostOnNode = true;
            Node ghostNode = ((Node) guider.getCurrentCell());
            openSet.add(ghostNode);
            distFromStart.put(ghostNode, 0);
            heuristicDistToGoal.put(ghostNode, getShortestDist(ghostNode));
        } else {
            getTwoStartNodes(guider);
            ghostOnNode = false;
        }

        if (target.getGuider().getCurrentCell() instanceof Node) {
            pacmanOnNode = true;
            targetNodes.add((Node) target.getGuider().getCurrentCell());
        } else {
            pacmanOnNode = false;
            List<Node> closestNodes = target.getGuider().getClosestNodes();

            for (Node node : closestNodes) {
                targetNodes.add(node);
            }
        }
        
        while (!openSet.isEmpty()) {
            Node current = getMinimum();
            if (targetNodes.contains(current)) {
                return current;
            }
            openSet.remove(current);
            closedSet.add(current);
            findNewRoute(current);
        }
        
        return target.getGuider().getClosestNodes().get(0);
    }

    private void findNewRoute(Node current) {
        for (Node neighbor : getNeighbors(current)) {
            boolean neighborVisited = closedSet.contains(neighbor);
            boolean neighborSeen = openSet.contains(neighbor);

            if (neighborVisited) {
                continue;
            }

            int tentativeDist = distFromStart.get(current) + getDistance(current, neighbor);

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
        distFromStart.put(nodeA, guider.distanceToNode(nodeA));
        heuristicDistToGoal.put(nodeA, guider.distanceToNode(nodeA) + getShortestDist(nodeA));
        Node nodeB = guider.getClosestNodes().get(1);
        distFromStart.put(nodeB, guider.distanceToNode(nodeB));
        heuristicDistToGoal.put(nodeB, guider.distanceToNode(nodeB) + getShortestDist(nodeB));

        for (Node neighbor : getNeighbors(nodeA)) {
            if (neighbor != nodeB) {
                openSet.add(neighbor);
                cameFrom.put(neighbor, nodeA);
                int distBetween = distFromStart.get(nodeA) + getDistance(nodeA, neighbor);
                distFromStart.put(neighbor, distBetween);
                heuristicDistToGoal.put(neighbor, distBetween + getShortestDist(neighbor));
            }
        }
        closedSet.add(nodeA);

        for (Node neighbor : getNeighbors(nodeB)) {
            if (neighbor != nodeA) {
                openSet.add(neighbor);
                cameFrom.put(neighbor, nodeB);
                int distBetween = distFromStart.get(nodeB) + getDistance(nodeB, neighbor);
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

    private int getShortestDist(Node current) {
        int xDist = Math.abs(current.getPositionX() - target.getGuider().getCurrentCell().getPositionX());
        int yDist = Math.abs(current.getPositionY() - target.getGuider().getCurrentCell().getPositionY());
        int dist = xDist + yDist;
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
        boolean onSamePath = false;
        
        if(guider instanceof PathGuide && target.getGuider() instanceof PathGuide) {
            if( ((PathGuide) guider).onSamePath(((PathGuide) target.getGuider())) ) {
                onSamePath = true;
                return checkPathNonOnNode(guider);
            }
        } else if (guider instanceof PathGuide && target.getGuider() instanceof Node) {
            if( ((Node) target.getGuider()).getPaths().contains(((PathGuide) guider).getPath()) ) {
                onSamePath = true;
                return checkPathPacmanOnNode(guider);
            }
        } else if (guider instanceof Node && target.getGuider() instanceof PathGuide) {
            if( ((Node) guider).getPaths().contains(((PathGuide) target.getGuider()).getPath()) ) {
                onSamePath = true;
                return checkPathGhostOnNode(guider);
            }
        } else {
            for(Path path : ((Node) target.getGuider()).getPaths()) {
                if( ((Node) guider).getPaths().contains(path) ) {
                    onSamePath = true;
                    return checkPathBothOnNodes(guider);
                }
            }   
        }
        
        if(!onSamePath) {
            Node targetNode = execute(guider);
            PathingWrapper bestPath = getPath(targetNode);
            
            Node nextNode;
            if (bestPath.getNodes().get(0) == guider.getCurrentCell()) {
                nextNode = bestPath.getNodes().get(1);
            } else {
                nextNode = bestPath.getNodes().get(0);
            }

            return guider.getDirectionOfNode(nextNode);
        }
        
        return null;
    }
    
    private Direction checkPathBothOnNodes(Guider guider) {
        return ((Node) guider).getDirectionOfNode((Node) target.getGuider());
    }
    
    private Direction checkPathNonOnNode(Guider guider) {
        List<Node> closestNodes = guider.getClosestNodes();
        Node nodeA = closestNodes.get(0);
        int distPacmanToNodeA = target.getGuider().distanceToNode(nodeA);
        int distGhostToNodeA = guider.distanceToNode(nodeA);
        
        if(distPacmanToNodeA < distGhostToNodeA) {
            return guider.getDirectionOfNode(nodeA);
        } else {
            return guider.getDirectionOfNode(nodeA).inverse();
        } 
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