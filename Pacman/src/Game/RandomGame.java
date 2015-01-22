/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import MapData.LevelData;
import MapData.RandomMapGenerator;
import MapData.XMLLevelWriter;
import Model.PlayGround;
import View.GameFrame;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author Regi
 */
public class RandomGame extends Game{

    RandomMapGenerator mapGen;
    LevelData data;
    
    public RandomGame(GameFrame gameFrame) {
        super(gameFrame);
        
    }
    
    @Override
    protected void loadLevel(Level level){
        Random rand = new Random();
        mapGen = new RandomMapGenerator();
        data = mapGen.getMap(10, 10, rand.nextInt());
        PlayGround playGround = new PlayGround(data, this);
        setPlayGround(playGround);
        playGround.addObserver(this);
                
    }
    
    public boolean save(String fileName){
        
        new XMLLevelWriter().writeLevel(data, fileName);
        return true;
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
