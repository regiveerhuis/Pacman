/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import MapData.LevelData;
import MapData.XMLLevelReader;
import Model.PlayGround;
import View.GameFrame;
import java.util.Observable;

/**
 *
 * @author Regi
 */
public class LoadedGame extends Game {

    
    public LoadedGame(GameFrame gameFrame, String levelName) {
        super(gameFrame);
        init(levelName);
    }
    
    @Override
    protected void loadLevel(Level level){
        
    }
    
    private void init(String levelName){
        LevelData data = new XMLLevelReader().loadRandomLevel(levelName);
        PlayGround playGround = new PlayGround(data, this);
        setPlayGround(playGround);
        playGround.addObserver(this);
    }
    
    @Override
    public void update(Observable obs, Object obj){
        if(obs instanceof PlayGround){
            stop();
        }else{
            super.update(obs, obj);
        }
    }
}
