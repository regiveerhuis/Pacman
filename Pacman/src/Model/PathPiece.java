/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Regi
 */
public class PathPiece extends TraversableCell {
    private Direction forwardDirection;
    private Direction backwardDirection;
    private Path path;

    public PathPiece(int positionX, int positionY, Path path, Direction forwardDirection, Direction backwardDirection) {
        super(positionX, positionY);
        this.forwardDirection = forwardDirection;
        this.backwardDirection = backwardDirection;
        this.path = path;
    }
 
    @Override
    public String toString(){
        return "pathpiece to " + forwardDirection + " and "+ backwardDirection;
    }
}
