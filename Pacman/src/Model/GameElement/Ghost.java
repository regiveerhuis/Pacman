/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GameElement;

import Game.TimerListenerHelper;
import Model.Cell.Cell;
import Model.Direction;
import Model.Cell.TraversableCell;
import View.RedrawEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

/**
 *
 * @author Regi
 */
public abstract class Ghost extends MovingElement {

    private Color color; //oranje rood cyaan roze
    private TraversableCell startCell;
    private PathFinder pathFinder;
    private int points = 200;
    TimerListenerHelper listenHelper;

    public Ghost(Color color, TraversableCell startCell, int walkSpeed) {
        super(startCell);
        this.color = color;
        GhostEventTracker.getGhostEventTracker().addGhost(this);
        listenHelper = new TimerListenerHelper(walkSpeed, this);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc(5, 5, Cell.CELL_SIZE - 10, Cell.CELL_SIZE, 0, 180);
        g.fillRect(5, (Cell.CELL_SIZE / 2), Cell.CELL_SIZE - 10, (Cell.CELL_SIZE / 2) - 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.move(pathFinder.getNextMove(getGuider()));
    }

    @Override
    public void move(Direction direction) {
        super.move(direction);
    }

    /*
     @Override
     public void Redraw(RedrawEvent e) {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
     */
    @Override
    public GameElementDeathEvent moverEnteredCell(MovingElement mover) {
        if (mover == this) {
            return null;
        }
        System.out.println("something happening");
        if (mover instanceof Pacman) {
            if (((Pacman) mover).isInvincible()) {
                this.die();
                ((Pacman) mover).addPoints(points);
                return new GameElementDeathEvent(null);
            } else {
                ((Pacman) mover).die();
                return new GameElementDeathEvent(null);
            }
        } else {
            return null;
        }
    }

    @Override
    public void die() {
        moveToStartPosition();
    }

    protected void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public TimerListenerHelper getTimerListenerHelper() {
        return listenHelper;
    }
}
