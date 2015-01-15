/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import Model.Direction;
import Model.GameElement.Ghost;
import Model.GameElement.MovingElement;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

/**
 *
 * @author Regi
 */
public class GhostNode extends Node {

    private Direction[] ghostDirections;

    public GhostNode(int positionX, int positionY, Map<Direction, Path> paths, Direction[] ghostDirections) {
        super(positionX, positionY, paths);
        this.ghostDirections = ghostDirections;
    }

    @Override
    public void tryMove(Direction direction, MovingElement movingElement) {
        if ((movingElement instanceof Ghost)) {
            super.tryMove(direction, movingElement);
        } else {
            boolean canEnter = true;
            for (Direction curDir : ghostDirections) {
                if (curDir == direction) {
                    canEnter = false;
                }
            }
            if (canEnter) {
                super.tryMove(direction, movingElement);
            }
        }
    }

    @Override
    protected void drawCell(Graphics g) {
        super.drawCell(g);
        g.setColor(Color.BLUE);
        for (Direction dir : ghostDirections) {
            for (int thickness = 0; thickness < 5; thickness++) {
                switch (dir) {
                    case NORTH:
                        for (int i = 0; i < 4; i++) {
                            g.drawLine(i * Cell.CELL_SIZE / 4, thickness, (i * Cell.CELL_SIZE / 4) + Cell.CELL_SIZE / 8, thickness);
                        }
                        break;
                    case SOUTH:
                        for (int i = 0; i < 4; i++) {
                            g.drawLine(i * Cell.CELL_SIZE / 4, Cell.CELL_SIZE - thickness, i * Cell.CELL_SIZE / 4 + Cell.CELL_SIZE / 8, Cell.CELL_SIZE- thickness);
                        }
                        break;
                    case EAST:
                        for (int i = 0; i < 4; i++) {
                            g.drawLine(Cell.CELL_SIZE - thickness, i * Cell.CELL_SIZE / 4, Cell.CELL_SIZE-thickness , (i * Cell.CELL_SIZE / 4) + Cell.CELL_SIZE / 8);
                        }
                        break;
                    case WEST:
                        for (int i = 0; i < 4; i++) {
                            g.drawLine(thickness, i * Cell.CELL_SIZE / 4, thickness, (i * Cell.CELL_SIZE / 4) + Cell.CELL_SIZE / 8);
                        }
                        break;
                }
            }
        }
    }
}
