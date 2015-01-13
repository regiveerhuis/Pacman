/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Cell;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Regi
 */
public class Wall extends Cell {
    
    public Wall(int positionX, int positionY){
        super(positionX, positionY);
    }
    
    @Override
    public String toString(){
        return "Wall";
    }
    
    @Override 
    public void draw(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(positionX*CELL_SIZE, positionY*CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
}
