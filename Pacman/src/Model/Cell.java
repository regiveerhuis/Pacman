/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Regi
 */
public abstract class Cell {
    private int positionX;
    private int positionY;
    
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
}
