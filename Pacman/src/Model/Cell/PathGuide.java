/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import Model.Direction;
import Model.GameElement.MovingElement;
import java.util.ArrayList;
import java.util.List;

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
        pathPiece.removeMover(movingElement);
        traversableCell.addMover(movingElement);
        if(traversableCell instanceof PathPiece){
            pathPiece = (PathPiece) traversableCell;
        }
    }

    @Override
    public TraversableCell getCurrentCell() {
        return pathPiece;
    }

    @Override
    public List<Node> getClosestNodes() {
        ArrayList<Node> n = new ArrayList();
        return n;
    }

    @Override
    public boolean isPossibleDirection(Direction direction) {
        return pathPiece.isPossibleDirection(direction);
    }

}
