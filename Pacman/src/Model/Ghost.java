/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import View.RedrawEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

/**
 *
 * @author Regi
 */
public class Ghost extends MovingElement {
    Color color; //oranje rood cyaan roze
    TraversableCell startCell;
    
    
    public Ghost(Color color, TraversableCell startCell){
        this.color = color;
        this.startCell = startCell;
    }
    
    public void draw(Graphics g){
        g.setColor(color);
        g.fillArc(5, 5, Cell.CELL_SIZE-10, Cell.CELL_SIZE, 0, 180);
        g.fillRect(5, (Cell.CELL_SIZE / 2), Cell.CELL_SIZE-10, (Cell.CELL_SIZE/2)-10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move(Direction direction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    @Override
    public void Redraw(RedrawEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
}
