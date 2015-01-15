/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.PlayGround;
import View.GameFrame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;


/**
 *
 * @author Regi
 */
public class Game extends Observable implements ActionListener, Observer {

    private PlayGround playGround;
    private Timer timer;
    private Player player;
    private Level level;
    private static final int TIMER_RESOLUTION = 10;
    
    public Game(GameFrame gameFrame) {
        addObserver(gameFrame);
        this.player = new Player(3);
        player.addObserver(this);
        level = Level.Level1;
        
        loadLevel(level);
    }
    
    private void loadLevel(Level level){
        if(timer!=null){timer.stop();}
        timer = new Timer(TIMER_RESOLUTION, this);
        playGround = new PlayGround(LevelData.getLevelData(level), this);
        playGround.addObserver(this);
    }
    
    public void draw(Graphics g){
        playGround.draw(g);
    }
    
    public void addKeyListener(KeyListener keyListener){
        setChanged();
        notifyObservers(keyListener);
    }
    
    public void addTimerListener(TimerListenerHelper listener){
        if(!timer.isRunning()){
            timer.start();
            timer.setRepeats(true);
        }
        timer.addActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setChanged();
        notifyObservers();
    }
    
    public Player getPlayer(){
        return player;
    }

    public int getPoints(){
        return player.getPoints();
    }
    
    public int getLives(){
        return player.getLives();
    }
    
    public void pause(){
        timer.stop();
    }
    
    public void resume(){
        timer.start();
    }
    
    @Override
    public void update(Observable obs, Object obj) {
        obs.getClass().toString();
        if(obs instanceof PlayGround){
      
            if(level.ordinal() >= Level.values().length-1){
                notifyObservers(true);
       
            }else{
                level = level.values()[level.ordinal()+1];
                loadLevel(level);
                
            }
        }else{
            setChanged();
            if((int)obj == 0){
                notifyObservers(true);
            }else{
                notifyObservers(false);
            }
        }
    }

}
