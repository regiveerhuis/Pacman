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
import javax.swing.Timer;


/**
 *
 * @author Regi
 */
public class Game implements ActionListener {

    private PlayGround playGround;
    private GameFrame gameFrame;
    private Timer timer;
    private static final int MOVE_SPEED = 1000;
    
    public Game(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        timer = new Timer(MOVE_SPEED, this);
        playGround = new PlayGround(LevelData.getLevelData(Level.Level), this);
        
    }
    
    public void draw(Graphics g){
        playGround.draw(g);
    }
    
    public void addKeyListener(KeyListener keyListener){
        gameFrame.addKeyListener(keyListener);
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
        gameFrame.repaint();
    }

}
