/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.PlayGround;
import java.awt.Graphics;

/**
 *
 * @author Regi
 */
public class Game {

    private PlayGround playGround;

    public Game() {
        playGround = LevelData.getPlayGround(Level.Level);
        
    }
    
    public void draw(Graphics g){
        playGround.draw(g);
    }
}
