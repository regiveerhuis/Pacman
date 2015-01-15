/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

import Model.Cell.Guider;
import Model.Direction;
import java.util.Random;

/**
 *
 * @author Regi
 */
public class RandomPathFinder extends PathFinder {

    public RandomPathFinder(PathingTarget target) {
        super(target);
    }

    @Override
    protected Direction getMove(Guider guider) {
        Random rand = new Random();
        Direction[] dirs = guider.getPossibleDirections();
        return dirs[rand.nextInt(dirs.length)];
    }
}
