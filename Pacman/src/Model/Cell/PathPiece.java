/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import Model.Direction;
import java.awt.Graphics;

/**
 *
 * @author Regi
 */
public class PathPiece extends TraversableCell {

    private Direction forwardDirection;
    private Direction backwardDirection;

    public PathPiece(int positionX, int positionY, Path path, Direction forwardDirection, Direction backwardDirection) {
        super(positionX, positionY);
        this.forwardDirection = forwardDirection;
        this.backwardDirection = backwardDirection;
    }

    @Override
    public Direction[] getPossibleDirections() {
        return new Direction[]{forwardDirection, backwardDirection};
    }

    @Override
    public boolean isPossibleDirection(Direction direction) {
        return direction == forwardDirection || direction == backwardDirection;
    }

    public boolean isForwardDirection(Direction direction) {
        return direction == forwardDirection;
    }
}
