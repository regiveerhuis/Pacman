/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

import Model.Cell.TraversableCell;
import java.awt.Color;

/**
 *
 * @author Regi
 */
public class RandomGhost extends Ghost{
    public RandomGhost(Color color, TraversableCell startCell, PathingTarget target, int walkSpeed){
        super(color, startCell, walkSpeed);
        
        setPathFinder(new RandomPathFinder(target));
    }
}
