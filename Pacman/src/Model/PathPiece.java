/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.awt.Graphics;

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
    public Direction[] getPossibleDirections(){
        return new Direction[]{forwardDirection, backwardDirection};
    }
    
    @Override
    public TraversableCell tryMove(Direction direction){
        if(direction == forwardDirection){
            return path.getNextTraversableCell(this);
        }else if(direction == backwardDirection){
            return path.getPreviousTraversableCell(this);
        }else{
            return null;
        }
    }
    
    @Override
    public String toString(){
        return "pathpiece to " + forwardDirection + " and "+ backwardDirection;
    }
}
