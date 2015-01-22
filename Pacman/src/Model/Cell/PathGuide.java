/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import Model.Direction;
import Model.GameElement.MovingElement;
import java.util.ArrayList;

/**
 *
 * @author Regi
 */
public class PathGuide implements Guider {

    private PathPiece pathPiece;
    private Path path;

    public PathGuide(Path path, PathPiece pathPiece) {
        this.path = path;
        this.pathPiece = pathPiece;
    }

    @Override
    public void tryMove(Direction direction, MovingElement movingElement) {
        if (isPossibleDirection(direction)) {
            TraversableCell nextCell;
            if (pathPiece.isForwardDirection(direction)) {
                nextCell = path.getNextTraversableCell(pathPiece);
            } else {
                nextCell = path.getPreviousTraversableCell(pathPiece);
            }

            moveToCell(movingElement, nextCell);
        }
    }

    private void moveToCell(MovingElement movingElement, TraversableCell traversableCell) {
        pathPiece.removeElement(movingElement);
        if (traversableCell instanceof PathPiece) {
            pathPiece = (PathPiece) traversableCell;
        }
        traversableCell.addMover(movingElement);

    }

    @Override
    public TraversableCell getCurrentCell() {
        return pathPiece;
    }

    @Override
    public ArrayList<Node> getClosestNodes() {
        ArrayList<Node> n = new ArrayList();
        n.add(path.getEndNode());
        n.add(path.getStartNode());
        return n;
    }

    @Override
    public boolean isPossibleDirection(Direction direction) {
        return pathPiece.isPossibleDirection(direction);
    }

    @Override
    public Guider guiderClone() {
        return new PathGuide(path, pathPiece);
    }

    @Override
    public Direction[] getPossibleDirections() {
        return pathPiece.getPossibleDirections();
    }

    @Override
    public void removeMover(MovingElement movingElement) {
        pathPiece.removeElement(movingElement);
    }

    @Override
    public int distanceToNode(Node node) {
        if (path.isEndNode(node)) {
            return path.getLength() - path.distanceToStart(pathPiece);
        } else if (path.isStartNode(node)) {
            return path.distanceToStart(pathPiece);
        } else {
            return -1;
        }
    }

    @Override
    public Direction getDirectionOfNode(Node targetNode) {
        if (targetNode == path.getStartNode()) {
            for (Direction dir : pathPiece.getPossibleDirections()) {
                if (!pathPiece.isForwardDirection(dir)) {
                    return dir;
                }
            }
        } else if (targetNode == path.getEndNode()) {
            for (Direction dir : pathPiece.getPossibleDirections()) {
                if (pathPiece.isForwardDirection(dir)) {
                    return dir;
                }
            }

        }
        return null;
    }

    public boolean onSamePath(PathGuide guider) {
        return guider.path == path;
    }
    
    public Path getPath() {
        return this.path;
    }
}
