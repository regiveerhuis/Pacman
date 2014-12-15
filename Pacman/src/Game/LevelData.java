/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.Cell;
import Model.Direction;
import Model.Node;
import Model.Pacman;
import Model.Path;
import Model.PathPiece;
import Model.PlayGround;
import Model.TraversableCell;
import Model.Wall;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Regi
 */
public class LevelData {

    private static Map<Level, LevelData> levels = new EnumMap<Level, LevelData>(Level.class);

    static {
        boolean[][] levelMap = {
            {false, false,  false, false,  false,   false,  false,  false,  false,  false,  false,  false,  false,  false},
            {false, true,   true,  true,   true,    true,   false,  true,   false,   true,   true,   true,   true,   false},
            {false, true,   false, true,   false,   true,   true,   true,   true,   false,  true,   false,  true,   false},
            {false, true,   true,  true,   false,   true,   false,  false,  true,   true,   true,   false,  true,   false},
            {false, true,   false, true,   false,   true,   true,   true,   false,   false,  true,   false,  true,   false},
            {false, true,   true,  true,   true,    true,   false,  true,   true,   true,   true,   true,   true,   false},
            {false, false,  false, false,  false,   false,  false,  false,  false,  false,  false,  false,  false,  false}
        };
        
        boolean[][] transposedMap = new boolean[levelMap[0].length][levelMap.length];
        for(int i = 0; i < levelMap.length; i++){
            for(int j = 0; j < levelMap[0].length; j++){
                transposedMap[j][i] = levelMap[i][j];
            }
        }
                
        levels.put(Level.Level, new LevelData(transposedMap, 1, 1, 5, 5));
    }

    private boolean[][] cellData;
    private int startPositionPacmanX;
    private int startPositionPacmanY;
    private int startPositionGhostX;
    private int startPositionGhostY;

    private LevelData(boolean[][] cellData, int startPacmanX, int startPacmanY, int startGhostX, int startGhostY) {
        this.cellData = cellData;
        this.startPositionGhostX = startGhostX;
        this.startPositionGhostY = startGhostY;
        this.startPositionPacmanX = startPacmanX;
        this.startPositionPacmanY = startPacmanY;
    }

    public static PlayGround loadPlayGround(Level level, Game game) {
        if (levels.containsKey(level)) {
            LevelData levelData = levels.get(level);
            boolean[][] cellData = levelData.cellData;
            Cell[][] cells = createNodes(cellData);
            initPaths(cells, cellData);
            PlayGround playGround = new PlayGround(cells);
            
            TraversableCell pacmanStartCell = (TraversableCell) cells[levelData.startPositionPacmanX][levelData.startPositionPacmanY];
            
            Pacman pacman = new Pacman(pacmanStartCell);
            pacmanStartCell.addMover(pacman);
            game.addKeyListener(pacman);
            game.addTimerListener(pacman);
            TraversableCell ghostStartCell = (TraversableCell) cells[levelData.startPositionGhostX][levelData.startPositionGhostY];
            
            
            
            return playGround;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //fills the Cell[][] array with walls and nodes
    private static Cell[][] createNodes(boolean[][] cellData) {
        Cell[][] cells = new Cell[cellData.length][cellData[0].length];

        for (int i = 0; i < cellData.length; i++) {
            for (int j = 0; j < cellData[i].length; j++) {
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

                    //if you're not on the top and the cell above you is not a wall, add a neighbour
                    if (j > 0 && cellData[i][j - 1]) {
                        directions.put(Direction.NORTH, null);
                    }

                    //if you're not on the bottom and the cell under you is not a wall, add a neighbour
                    if (j + 1 < cellData[i].length && cellData[i][j + 1]) {
                        directions.put(Direction.SOUTH, null);

                    }
                    if (directions.size() != 2) {
                        cells[i][j] = new Node(i, j, directions);
                    }
                }
            }
        }

        return cells;
    }

    //fill nulls with pathpieces, and init the paths of the nodes
    private static void initPaths(Cell[][] nodes, boolean[][] cellData) {
        for (Cell[] cells : nodes) {
            for (Cell cell : cells) {
                if (cell instanceof Node) {
                    initNodePaths((Node) cell, nodes, cellData);
                }
            }
        }
    }

    //makes, fills and returns a path.
    private static void initNodePaths(Node node, Cell[][] cells, boolean[][] cellData) {
        for (Direction direction : node.getPossibleDirections()) {

            if (!node.pathValid(direction)) {
                makeNodePath(node, cells, cellData, direction);

            }
        }
    }

    private static Path makeNodePath(Node node, Cell[][] cells, boolean[][] cellData, Direction direction) {
        Node startNode = node;
        Stack<int[]> pathPieceLocations = new Stack<>();
        Stack<Direction[]> pathPieceDirections = new Stack<>();
        Direction firstDirection = direction;
        Direction prevDirection = direction;
        int curX = node.getPositionX();
        int curY = node.getPositionY();
        Node endNode = null;

        while (endNode == null) {
            switch (direction) {
                case NORTH:
                    curY--;
                    break;

                case SOUTH:
                    curY++;
                    break;

                case EAST:
                    curX++;
                    break;

                case WEST:
                    curX--;
                    break;
            }

            int[] arr = {curX, curY};

            if (cells[curX][curY] != null && cells[curX][curY] instanceof Node) {
                endNode = (Node) cells[curX][curY];

            } else {
                prevDirection = direction;
                direction = getNextDirections(curX, curY, cellData, direction).get(0);
                Direction[] dir = {prevDirection.inverse(), direction};
                pathPieceLocations.add(arr);
                pathPieceDirections.add(dir);
            }
        }

        ArrayList<PathPiece> pathPieces = new ArrayList();
        Path path = new Path(endNode, node, pathPieces);
        endNode.setPath(prevDirection.inverse(), path);
        node.setPath(firstDirection, path);
        while(pathPieceLocations.size() != 0) {
            int[] locations = pathPieceLocations.pop();
            Direction [] directions = pathPieceDirections.pop();
            PathPiece pathPiece = new PathPiece(locations[0], locations[1], path, directions[0], directions[1]);
            System.out.println(pathPiece.getPositionX() + ", " + pathPiece.getPositionY());
            pathPieces.add(pathPiece);
            cells[pathPiece.getPositionX()][pathPiece.getPositionY()] = pathPiece;
        }
        return path;
    }

    private static ArrayList<Direction> getNextDirections(int x, int y, boolean[][] cellData, Direction prevDirection) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (direction.inverse() != prevDirection) {
                try {
                    switch (direction) {
                        case NORTH:
                            if (cellData[x][y - 1]) {
                                directions.add(direction);
                            }
                            break;

                        case SOUTH:
                            if (cellData[x][y + 1]) {
                                directions.add(direction);
                            }
                            break;

                        case EAST:
                            if (cellData[x + 1][y]) {
                                directions.add(direction);
                            }
                            break;
                        case WEST:
                            if (cellData[x - 1][y]) {
                                directions.add(direction);
                            }
                            break;

                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
            }
        }
        return directions;
    }

}
