/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

import Model.Cell.Cell;
import Model.Cell.TraversableCell;
import Model.PlayGround;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Regi
 */
public class TrackingGhost extends Ghost{

    public TrackingGhost(Color color, TraversableCell startCell, PathingTarget target, PlayGround playGround, int walkSpeed) {
        super(color, startCell, walkSpeed);
        setPathFinder(new ShortestPathFinder(target, playGround));
    }
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.WHITE);
        g.fillOval(Cell.CELL_SIZE/4, Cell.CELL_SIZE/4, Cell.CELL_SIZE/4, Cell.CELL_SIZE/4);
    }
}
