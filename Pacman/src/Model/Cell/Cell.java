/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Cell;

import java.awt.Graphics;

/**
 *
 * @author Regi
 */
public abstract class Cell {
    protected int positionX;
    protected int positionY;
    public static final int CELL_SIZE = 50;
    
    public Cell(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * @return the positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * @return the positionY
     */
    public int getPositionY() {
        return positionY;
    }
    
    public abstract void draw(Graphics g);
}
