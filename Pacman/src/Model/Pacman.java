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
    boolean biteFrame = false;

    public Pacman(TraversableCell startCell) {
        curCell = startCell;
        startPosition = startCell;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                nextDirection = Direction.NORTH;
                break;
            case KeyEvent.VK_RIGHT:
                nextDirection = Direction.EAST;
                break;
            case KeyEvent.VK_DOWN:
                nextDirection = Direction.SOUTH;
                break;
            case KeyEvent.VK_LEFT:
                nextDirection = Direction.WEST;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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

        int arcCenter = 0;
        if (curDirection != null) {
            arcCenter = curDirection.toDegrees();
        }

        if (biteFrame) {
            g.fillArc(5, 5, Cell.CELL_SIZE - 10, Cell.CELL_SIZE - 10, arcCenter + 10, 340);
        } else {

            g.fillArc(5, 5, Cell.CELL_SIZE - 10, Cell.CELL_SIZE - 10, arcCenter + 45, 270);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tryNextMove();
        biteFrame = !biteFrame;
    }

    @Override
    public void move(Direction direction) {
        TraversableCell newCell = curCell.tryMove(direction);
        System.out.println("try move");
        if (newCell != null) {
            curCell.removeMover(this);
            curCell = newCell;
            newCell.addMover(this);
            System.out.println("moved to :" + curCell.getPositionX() + ", " + curCell.getPositionY());
        }
    }

    /*
    @Override
    public void Redraw(RedrawEvent e) {

    
    }
    */
}
