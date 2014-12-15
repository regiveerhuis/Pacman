/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Regi
 */
public class TraversableCell extends Cell {

    public TraversableCell(int positionX, int positionY) {
        super(positionX, positionY);
    }
    
    public Direction[] getPossibleDirections() {
        return null;
    }
    
    public TraversableCell TryMove (Direction d) {
        return null;
    }
    
    public void draw (Graphics g) {
        
    }
}
