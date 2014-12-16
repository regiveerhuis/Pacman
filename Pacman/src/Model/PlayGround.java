/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Game.Game;
import Game.LevelData;
import java.awt.Color;
import java.awt.Graphics;
import java.util.EnumMap;

/**
 *
 * @author Regi
 */
public class PlayGround {

    private Cell[][] cells;

    public Cell[][] getCells() {
        return cells;
    }

    public PlayGround(LevelData levelData, Game game) {
        boolean[][] cellData = levelData.getCellData();
        cells = new Cell[cellData.length][cellData[0].length];
        createNodes(cellData);

        TraversableCell pacmanStartCell = (TraversableCell) cells[levelData.getStartPositionPacmanX()][levelData.getStartPositionPacmanY()];

        Pacman pacman = new Pacman(pacmanStartCell);
        pacmanStartCell.addMover(pacman);
        game.addKeyListener(pacman);
        game.addTimerListener(pacman);
        TraversableCell[] ghostStartCell = new TraversableCell[levelData.getStartPositionGhostX().length];
        Color[] ghostColors = {Color.ORANGE, Color.RED, Color.CYAN, Color.PINK}; //oranje rood cyaan roze
        for (int i = 0; i < levelData.getStartPositionGhostX().length; i++) {
            ghostStartCell[i] = (TraversableCell) cells[levelData.getStartPositionGhostX()[i]][levelData.getStartPositionGhostY()[i]];
            ghostStartCell[i].addMover(new Ghost(ghostColors[i], ghostStartCell[i]));
        }

    }

    public void draw(Graphics g) {
        for (Cell[] cellArr : cells) {
            for (Cell cell : cellArr) {
                
                    cell.draw(g);
                
            }
        }
    }

    //fills the Cell[][] array with walls and nodes
    private void createNodes(boolean[][] cellData) {
        for (int i = 0; i < cellData.length; i++) {
            for (int j = 0; j < cellData[i].length; j++) {
                System.out.println("hallo: " + i + ", " + j);
                if (!cellData[i][j]) {
                    cells[i][j] = new Wall(i, j);
                } else {

                    EnumMap<Direction, Path> directions = new EnumMap<Direction, Path>(Direction.class);
                    //if you're not on the far left and the cell to the left of you is not a wall, add a neighbour
                    if (i > 0 && cellData[i - 1][j]) {
                        directions.put(Direction.WEST, null);
                    }

                    //if you're not on the far right and the cell to the right of you is not a wall, add a neighbour
                    if (i + 1 < cellData.length && cellData[i + 1][j]) {
                        directions.put(Direction.EAST, null);
                    }

                    //if you're not at the top and the cell above you is not a wall, add a neighbour
                    if (j > 0 && cellData[i][j - 1]) {
                        directions.put(Direction.NORTH, null);
                    }

                    //if you're not at the bottom and the cell under you is not a wall, add a neighbour
                    if (j + 1 < cellData[i].length && cellData[i][j + 1]) {
                        directions.put(Direction.SOUTH, null);

                    }

                    if (directions.size() != 2) {
                        System.out.println("hallo1");
                        cells[i][j] = new Node(i, j, directions);
                        System.out.println("hallo2");
                    }
                }
            }
        }
        
            for (Cell[] cellArr : cells) {
                for (Cell cell : cellArr) {
                    if (cell instanceof Node) {
                        ((Node) cell).initNode(cells, cellData);
                    }
                }
            }

    }

}
