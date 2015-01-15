/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Cell.Cell;
import Model.Cell.TraversableCell;
import Model.Cell.PathPiece;
import Model.Cell.Wall;
import Model.Cell.Node;
import Model.Cell.PathGuide;
import Model.Cell.Path;
import Model.GameElement.Pacman;
import Model.GameElement.Ghost;
import Game.Game;
import Game.KeyEventWrapper;
import Game.LevelData;
import Model.Cell.GhostNode;
import Model.GameElement.Cherry;
import Model.GameElement.MovingElement;
import Model.GameElement.PacDot;
import Model.GameElement.RandomGhost;
import Model.GameElement.SuperDot;
import Model.GameElement.TrackingGhost;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Regi
 */
public class PlayGround extends Observable implements Observer {

    private Cell[][] cells;
    private HashSet<Path> paths = new HashSet();
    private int startPacDotAmount = 0;
    private int pacDotAmount = 0;
    private boolean hasCherrySpawned = false;

    public Cell[][] getCells() {
        return cells;
    }

    public PlayGround(LevelData levelData, Game game) {
        boolean[][] cellData = levelData.getCellData();
        cells = new Cell[cellData.length][cellData[0].length];
        createNodes(levelData);
        indexPaths();

        TraversableCell pacmanStartCell = (TraversableCell) cells[levelData.getStartPositionPacmanX()][levelData.getStartPositionPacmanY()];
        Pacman pacman = new Pacman(pacmanStartCell, game.getPlayer());

        if (pacmanStartCell instanceof Node) {
            pacman.setGuider((Node) pacmanStartCell);
        } else {
            for (Path path : paths) {
                if (path.containsCell(pacmanStartCell)) {
                    pacman.setGuider(new PathGuide(path, (PathPiece) pacmanStartCell));
                    break;
                }
            }
        }
        pacmanStartCell.addMover(pacman);
        KeyEventWrapper keyEventWrapper = new KeyEventWrapper();
        keyEventWrapper.addListener(pacman);
        game.addKeyListener(keyEventWrapper);
        game.addTimerListener(pacman.getTimerListenerHelper());
        TraversableCell[] ghostStartCell = new TraversableCell[levelData.getStartPositionGhostX().length];
        Color[] ghostColors = {Color.ORANGE, Color.RED, Color.CYAN, Color.PINK}; //oranje rood cyaan roze
        int randomGhostCounter = 0;
        for (int i = 0; i < levelData.getStartPositionGhostX().length; i++) {
            ghostStartCell[i] = (TraversableCell) cells[levelData.getStartPositionGhostX()[i]][levelData.getStartPositionGhostY()[i]];
            Ghost ghost = null;
            if (randomGhostCounter < 2) {
                ghost = new RandomGhost(ghostColors[i], ghostStartCell[i], pacman, 40);
                randomGhostCounter++;
            } else {
                ghost = new TrackingGhost(ghostColors[i], ghostStartCell[i], pacman, this, 30 + i * 10);
            }
            ghostStartCell[i].addMover(ghost);
            game.addTimerListener(ghost.getTimerListenerHelper());
            if (ghostStartCell[i] instanceof Node) {
                ghost.setGuider((Node) ghostStartCell[i]);
            } else {
                for (Path path : paths) {
                    if (path.containsCell(ghostStartCell[i])) {
                        ghost.setGuider(new PathGuide(path, (PathPiece) ghostStartCell[i]));
                        break;
                    }
                }
            }
        }

        for (int[] superDotLocation : levelData.getSuperDotLocations()) {
            if (cells[superDotLocation[0]][superDotLocation[1]] instanceof TraversableCell) {
                ((TraversableCell) cells[superDotLocation[0]][superDotLocation[1]]).addStatic(new SuperDot());
            }
        }
        
        for (Cell[] c : cells) {
            for (Cell cell : c) {
                if (cell instanceof TraversableCell) {
                    if (((TraversableCell) cell).isEmpty() && !levelData.isGhostPiece(cell.getPositionX(), cell.getPositionY())) {
                        PacDot pacDot = new PacDot();
                        pacDot.addObserver(this);
                        ((TraversableCell) cell).addStatic(pacDot);
                        startPacDotAmount++;
                        pacDotAmount++;
                    }
                }
            }
        }
        

    }

    public void draw(Graphics g) {
        for (Cell[] cellArr : cells) {
            for (Cell cell : cellArr) {

                cell.draw(g);

            }
        }
    }

    //Finds the path containing the cell given. returns null if the cell belongs to no path (e.g. if it does not exist, or if it is a node)
    public Path findPathByCell(TraversableCell cell) {
        if (cell instanceof Node) {
            return null;
        } else {
            for (Path path : paths) {
                if (path.containsCell(cell)) {
                    return path;
                }
            }
        }
        return null;
    }

    //fills the Cell[][] array with walls and nodes
    private void createNodes(LevelData levelData) {
        boolean[][] cellData = levelData.getCellData();
        for (int i = 0; i < cellData.length; i++) {
            for (int j = 0; j < cellData[i].length; j++) {
                if (!cellData[i][j]) {
                    cells[i][j] = new Wall(i, j);
                } else {
                    boolean isGhostNode = false;
                    ArrayList<Direction> ghostDirs = new ArrayList();
                    EnumMap<Direction, Path> directions = new EnumMap<Direction, Path>(Direction.class);
                    //if you're not on the far left and the cell to the left of you is not a wall, add a neighbour
                    if (i > 0 && cellData[i - 1][j]) {
                        directions.put(Direction.WEST, null);
                        if (levelData.isGhostPiece(i - 1, j)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.WEST);
                        }
                    }

                    //if you're not on the far right and the cell to the right of you is not a wall, add a neighbour
                    if (i + 1 < cellData.length && cellData[i + 1][j]) {
                        directions.put(Direction.EAST, null);
                        if (levelData.isGhostPiece(i + 1, j)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.EAST);
                        }
                    }

                    //if you're not at the top and the cell above you is not a wall, add a neighbour
                    if (j > 0 && cellData[i][j - 1]) {
                        directions.put(Direction.NORTH, null);
                        if (levelData.isGhostPiece(i, j - 1)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.NORTH);
                        }
                    }

                    //if you're not at the bottom and the cell under you is not a wall, add a neighbour
                    if (j + 1 < cellData[i].length && cellData[i][j + 1]) {
                        directions.put(Direction.SOUTH, null);
                        if (levelData.isGhostPiece(i, j + 1)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.SOUTH);
                        }
                    }

                    if (isGhostNode) {
                        cells[i][j] = new GhostNode(i, j, directions, ghostDirs.toArray(new Direction[ghostDirs.size()]));
                    } else if (directions.size() != 2) {
                        cells[i][j] = new Node(i, j, directions);
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

    private void indexPaths() {
        for (Cell[] c : cells) {
            for (Cell cell : c) {
                if (cell instanceof Node) {
                    paths.addAll(((Node) cell).getPaths());
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        pacDotAmount--;
        System.out.println(pacDotAmount);
        if (pacDotAmount <= startPacDotAmount / 2 && !hasCherrySpawned) {
            spawnCherry();
            hasCherrySpawned = true;
        } 
        if (pacDotAmount <= 0) {
            setChanged();
            notifyObservers();
        }
    }

    private void spawnCherry() {
        ArrayList<TraversableCell> possibleCells = new ArrayList();
        for (Cell[] c : cells) {
            for (Cell cell : c) {
                if (cell instanceof TraversableCell) {
                    if (((TraversableCell) cell).isEmpty()) {
                        possibleCells.add(((TraversableCell) cell));
                    }
                }
            }
        }

        Random rand = new Random();
        possibleCells.get(rand.nextInt(possibleCells.size())).addStatic(new Cherry());
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> returnList = new ArrayList();
        for (Cell[] c : cells) {
            for (Cell cell : c) {
                if (cell instanceof Node) {
                    returnList.add((Node)cell);
                }
            }
        }
        return returnList;
    }

    public HashSet<Path> getPaths() {
        return paths;
    }

}
