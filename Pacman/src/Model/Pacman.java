/*
 * 
 */

package Model;

import View.RedrawEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author Matthias
 */
public class Pacman extends MovingElement {
    private Direction nextDirection;
    private Direction curDirection;
    private TraversableCell curCell;
    private TraversableCell startPosition;
    
    public Pacman(TraversableCell startCell) {
        curCell = startCell;
        startPosition = startCell;
    }
    
    public void KeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: 
                setNextDirection(Direction.NORTH);
            case KeyEvent.VK_RIGHT:
                setNextDirection(Direction.EAST);
            case KeyEvent.VK_DOWN:
                setNextDirection(Direction.SOUTH);
            case KeyEvent.VK_LEFT:
                setNextDirection(Direction.WEST);
        }
    }
    
    public void KeyTyped (KeyEvent e) {
        
    }
    
    public void KeyReleased (KeyEvent e) {
        
    }
    
    public void setNextDirection (Direction d) {
        nextDirection = d;
    }
    
    public Direction getNextDirection () {
        return nextDirection;
    }
    
    public void setCurDirection (Direction d) {
        curDirection = d;
    }
    
    @Override
    public Direction getCurDirection() {
        return curDirection;
    }
    
    public void tryNextMove() {
        Direction nextMove = null;
        
        for(Direction d : curCell.getPossibleDirections()) {
            if(d == nextDirection) {
                nextMove = nextDirection;
            }
        }
        
        if(nextMove != null) {
            move(nextMove);
        } else {
            for(Direction d : curCell.getPossibleDirections()) {
                if(d == curDirection) {
                    move(curDirection);
                }
            }
        }
    }
    
    @Override
    public void draw (Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(5, 5, Cell.CELL_SIZE - 10, Cell.CELL_SIZE - 10);
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
        
    }
    
    @Override
    public void move (Direction d) {
        curCell = curCell.TryMove(d);
    }
    
    @Override
    public void Redraw(RedrawEvent e) {
        
    }
}
