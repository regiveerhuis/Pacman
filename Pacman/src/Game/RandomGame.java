/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import MapData.RandomMapGenerator;
import MapData.XMLLevelReader;
import Model.PlayGround;
import View.GameFrame;
import java.util.Observable;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Regi
 */
public class RandomGame extends Game{

    RandomMapGenerator mapGen;
    
    public RandomGame(GameFrame gameFrame) {
        super(gameFrame);
        
    }
    
    @Override
    protected void loadLevel(Level level){
        Random rand = new Random();
        mapGen = new RandomMapGenerator();
        
        PlayGround playGround = new PlayGround(mapGen.getMap(16, 16, rand.nextInt()), this);
        setPlayGround(playGround);
        playGround.addObserver(this);
    }
    
    @Override
    public void update(Observable obs, Object obj){
        if(obs instanceof PlayGround){
           loadLevel(null);
        }else{
            super.update(obs, obj);
        }
    }

}
