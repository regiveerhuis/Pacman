/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GameElement;

import Model.Cell.Guider;
import Model.Cell.TraversableCell;
import Model.Direction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Regi
 */
public abstract class PathFinder {

    private PathingTarget target;

    public PathFinder(PathingTarget target) {
        this.target = target;
    }

    protected PathingTarget getTarget() {
        return target;
    }

    public Direction getNextMove(Guider guider) {
       if (getTarget().moveTo()) {
            return getMove(guider);
        } else {
            TraversableCell targetCell = getTarget().getGuider().getCurrentCell();
            TraversableCell curCell = guider.getCurrentCell();
            ArrayList<Direction> possDirs = new ArrayList(Arrays.asList(guider.getPossibleDirections()));

            if (targetCell.getPositionX() > curCell.getPositionX()) {
                possDirs.remove(Direction.EAST);
            } else if (targetCell.getPositionX() < curCell.getPositionX()) {
                possDirs.remove(Direction.WEST);
            }

            if (targetCell.getPositionY() > curCell.getPositionY()) {
                possDirs.remove(Direction.SOUTH);
            } else if (targetCell.getPositionY() < curCell.getPositionY()) {
                possDirs.remove(Direction.NORTH);
            }
            Random rand = new Random();
            
            if (possDirs.isEmpty()) {
                Direction[] dirs = guider.getPossibleDirections();
                return dirs[rand.nextInt(dirs.length)];
            }else{
                return possDirs.get(rand.nextInt(possDirs.size()));
            }
        }
    }

    protected abstract Direction getMove(Guider guider);
}
