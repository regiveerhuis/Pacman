/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

import Model.Cell.Cell;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Regi
 */
public class Cherry extends StaticElement{

    public Cherry(){
        super(100);
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(Cell.CELL_SIZE*3/4, Cell.CELL_SIZE/2, Cell.CELL_SIZE/4, Cell.CELL_SIZE/4);
        g.fillOval(Cell.CELL_SIZE/2, Cell.CELL_SIZE*3/4, Cell.CELL_SIZE/4, Cell.CELL_SIZE/4);
        g.setColor(Color.GREEN);
        g.drawLine(Cell.CELL_SIZE/2, Cell.CELL_SIZE*3/4, Cell.CELL_SIZE/4, Cell.CELL_SIZE/4);
        g.drawLine(Cell.CELL_SIZE*3/4, Cell.CELL_SIZE/2, Cell.CELL_SIZE/4, Cell.CELL_SIZE/4);
    }
    
    
    @Override
    public GameElementDeathEvent moverEnteredCell(MovingElement mover) {
       if(mover instanceof Pacman){
           ((Pacman) mover).addPoints(this.getPoints());
           this.die();
           return new GameElementDeathEvent(this);
       }else{
           return null;
       }
    }

    @Override
    public void die() {
    }
}
