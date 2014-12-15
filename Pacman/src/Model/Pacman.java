/*
 * 
 */
package Model;

import View.RedrawEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Matthias
 */
public class Pacman extends MovingElement implements KeyListener {

    private Direction nextDirection;
    private Direction curDirection;
    private TraversableCell curCell;
    private TraversableCell startPosition;

    public Pacman(TraversableCell startCell) {
        curCell = startCell;
        startPosition = startCell;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                setNextDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
                setNextDirection(Direction.EAST);
                break;
            case KeyEvent.VK_DOWN:
                setNextDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                setNextDirection(Direction.WEST);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setNextDirection(Direction d) {
        nextDirection = d;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setCurDirection(Direction d) {
        curDirection = d;
    }

    @Override
    public Direction getCurDirection() {
        return curDirection;
    }

    public void tryNextMove() {
        for (Direction d : curCell.getPossibleDirections()) {
            if (d == nextDirection) {
                curDirection = nextDirection;
                nextDirection = null;
                move(curDirection);
                return;
            }
        }

        for (Direction d : curCell.getPossibleDirections()) {
            if (d == curDirection) {
                move(curDirection);
                break;
            }
        }

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(5, 5, Cell.CELL_SIZE - 10, Cell.CELL_SIZE - 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tryNextMove();
    }

    @Override
    public void move(Direction d) {
        TraversableCell newCell = curCell.tryMove(d);
        System.out.println("try move");
        if (newCell != null) {
            curCell.removeMover(this);
            curCell = newCell;
            newCell.addMover(this);
            System.out.println("moved to :" + curCell.getPositionX() + ", " + curCell.getPositionY());
        }
    }

    @Override
    public void Redraw(RedrawEvent e) {

    }
}
