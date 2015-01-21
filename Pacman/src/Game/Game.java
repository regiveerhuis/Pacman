/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import MapData.XMLLevelReader;
import Model.PlayGround;
import View.GameFrame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Regi
 */
public class Game extends Observable implements KeyListener, ActionListener, Observer {

    ArrayList<KeyListener> keyListeners = new ArrayList();
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
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(TIMER_RESOLUTION, this);

        loadLevel(level);
    }

    protected void loadLevel(Level level) {
        stop();
        timer = new Timer(TIMER_RESOLUTION, this);
        playGround = new PlayGround(new XMLLevelReader().loadNormalLevel(level), this);
        playGround.addObserver(this);

    }

    public void draw(Graphics g) {
        playGround.draw(g);
    }

    public void addKeyListener(KeyListener keyListener) {
        keyListeners.add(keyListener);
    }

    public void addTimerListener(TimerListenerHelper listener) {
        if (!timer.isRunning()) {
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

    public Player getPlayer() {
        return player;
    }

    public int getPoints() {
        return player.getPoints();
    }

    public int getLives() {
        return player.getLives();
    }

    public void pause() {
        timer.stop();
    }

    public void resume() {
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        timer = null;
        if (keyListeners != null) {
            keyListeners.clear();
        }
    }

    @Override
    public void update(Observable obs, Object obj) {

        if (obs instanceof PlayGround) {
            if (level.ordinal() >= Level.values().length - 1) {
                //game over: no more levels
                stop();
                notifyObservers(true);
            } else {
                level = level.values()[level.ordinal() + 1];
                notifyObservers(false);
                loadLevel(level);
            }

        } else if (obs instanceof Player) {
            setChanged();
            if ((int) obj <= 0) {
                //game over: no more lives
                stop();
                notifyObservers(true);
            } else {
                notifyObservers(false);
            }
        }
    }

    protected void setPlayGround(PlayGround playGround) {
        this.playGround = playGround;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        for (KeyListener listener : keyListeners) {
            listener.keyTyped(ke);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        for (KeyListener listener : keyListeners) {
            listener.keyPressed(ke);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        for (KeyListener listener : keyListeners) {
            listener.keyReleased(ke);
        }
    }

}
