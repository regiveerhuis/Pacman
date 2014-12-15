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
public class PlayGround {
    private Cell[][] cells;   
 
    public Cell[][] getCells(){
        return cells;
    }
    
    public PlayGround(Cell[][] cells){
        this.cells = cells;
    }
    
    public void draw(Graphics g){
        
    }
}