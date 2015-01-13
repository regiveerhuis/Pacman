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
public class GameElementDeathEvent {
    GameElement element;
    
    public GameElementDeathEvent(GameElement element){
        this.element = element;
    }
    
    public GameElement GetElement(){
        return element;
    }
}
