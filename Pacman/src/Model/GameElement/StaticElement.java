/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

/**
 *
 * @author Regi
 */
public abstract class StaticElement extends GameElement {
    private int points;
    
    public StaticElement(int points){
        this.points = points;
    }
    
    public int getPoints(){
        return points;
    }
}
