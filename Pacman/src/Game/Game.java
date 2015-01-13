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
    private static final int MOVE_SPEED = 1000;
    
    public Game(GameFrame gameFrame) {
        addObserver(gameFrame);
        this.player = new Player(3);
        player.addObserver(this);
        timer = new Timer(MOVE_SPEED, this);
        playGround = new PlayGround(LevelData.getLevelData(Level.Level), this); 
    }
    
    public void draw(Graphics g){
        playGround.draw(g);
    }
    
    public void addKeyListener(KeyListener keyListener){
        setChanged();
        notifyObservers(keyListener);
    }
    
    public void addTimerListener(ActionListener actionListener){
        if(!timer.isRunning()){
            timer.start();
            timer.setRepeats(true);
        }
        timer.addActionListener(actionListener);
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
    
    @Override
    public void update(Observable obs, Object obj) {
        setChanged();
        notifyObservers();
    }

}
