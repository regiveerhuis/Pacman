/*
 * 
 */
package Model.GameElement;

import Game.Player;
import Model.Cell.Cell;
import Model.Direction;
import Model.Cell.TraversableCell;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

/**
 * @author Matthias
 */
public class Pacman extends MovingElement implements DirectionEventListener, PathingTarget {

    private Direction nextDirection;
    private Direction curDirection;
    private TraversableCell startPosition;
    private boolean biteFrame = false;
    private boolean invincible;
    private Player player;

    public Pacman(TraversableCell startCell, Player player) {
        startPosition = startCell;
        this.player = player;
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

    @Override
    public GameElementDeathEvent moverEnteredCell(MovingElement mover) {
        if (mover instanceof Ghost) {
            if (invincible) {
                mover.die();
                return new GameElementDeathEvent(mover);
            } else {
                this.die();
                return new GameElementDeathEvent(this);
            }
        } else {
            return null;
        }
    }

    @Override
    public void die() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isInvincible() {
       return invincible;
    }

    public void addPoints(int points) {
       player.addPoints(points);
    }

}
