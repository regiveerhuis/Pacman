/*
 * 
 */
package Model.GameElement;

import Model.Cell.Cell;
import Model.Direction;
import Model.GameElement.PathingTarget;
import Model.Cell.TraversableCell;
import View.RedrawEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Matthias
 */
public class Pacman extends MovingElement implements DirectionEventListener, PathingTarget {

    private Direction nextDirection;
    private Direction curDirection;

    private TraversableCell startPosition;
    boolean biteFrame = false;
    private boolean invincible;

    public Pacman(TraversableCell startCell) {
        startPosition = startCell;
    }

    public void tryNextMove() {
        if (!(nextDirection == null && curDirection == null)) {
            if (getGuider().isPossibleDirection(nextDirection)) {
                curDirection = nextDirection;
                nextDirection = null;
                move(curDirection);
            } else if (getGuider().isPossibleDirection(curDirection)) {
                move(curDirection);
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

    /*
     @Override
     public void move(Direction direction) {
     TraversableCell newCell = curCell.tryMove(direction);
     if (newCell != null) {
     curCell.removeMover(this);
     curCell = newCell;
     newCell.addMover(this);
     }
     }
     */
    /*
     @Override
     public void Redraw(RedrawEvent e) {

    
     }
     */
    @Override
    public void directionEvent(Direction direction) {
        nextDirection = direction;
    }

    @Override
    public boolean moveTo() {
        return !invincible;
    }

}
