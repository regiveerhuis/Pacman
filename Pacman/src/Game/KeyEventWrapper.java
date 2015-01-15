/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Model.Direction;
import Model.GameElement.DirectionEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 *
 * @author Regi
 */
public class KeyEventWrapper implements KeyListener {
    
    ArrayList<DirectionEventListener> directionEventListeners = new ArrayList();
    
    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                direction = Direction.NORTH;
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.EAST;
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.SOUTH;
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.WEST;
                break;
        }
        if(direction != null){
            notify(direction);
        }
        System.out.println("vla");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void notify(Direction direction){
        for(DirectionEventListener l : directionEventListeners){
            l.directionEvent(direction);
        }
    }
    
    public void addListener(DirectionEventListener directionEventListener){
        directionEventListeners.add(directionEventListener);
    }
    
    public void removeListener(DirectionEventListener directionEventListener){
        directionEventListeners.remove(directionEventListener);
    }
}
