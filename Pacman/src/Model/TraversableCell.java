/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Regi
 */
public class TraversableCell extends Cell {

    private ArrayList<MovingElement> movers = new ArrayList();
    
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
        g.setColor(Color.BLACK);
        g.translate(positionX*CELL_SIZE, positionY*CELL_SIZE);
        g.drawRect(0, 0, CELL_SIZE, CELL_SIZE);
        
        for(MovingElement mover : movers){
            mover.draw(g);
        }
        g.translate(-positionX*CELL_SIZE, -positionY*CELL_SIZE);
    }
    
    public void addMover(MovingElement mover){
        movers.add(mover);
    }
    
}
