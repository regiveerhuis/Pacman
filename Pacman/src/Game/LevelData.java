/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.Cell;
import Model.Direction;
import Model.Node;
import Model.Path;
import Model.PathPiece;
import Model.PlayGround;
import Model.TraversableCell;
import Model.Wall;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 *
 * @author Regi
 */
public class LevelData {

    private static Map<Level, LevelData> levels = new HashMap<Level, LevelData>();

    static {

    }

    private boolean[][] cellData;
    private int startPositionPacmanX;
    private int startPositionPacmanY;
    private int startPositionGhostX;
    private int startPositionGhostY;

    private LevelData() {

    }

    public static PlayGround getPlayGround(Level level) {
        if (levels.containsKey(level)) {

            LevelData levelData = levels.get(level);
            boolean[][] cellData = levelData.cellData;
            Cell[][] cells = createNodes(cellData);

        } else {
            throw new IllegalArgumentException();
        }

        return null;
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
                        cells[i][j] = new Node(i, j, new EnumMap<Direction, Path>(Direction.class));
                    }
                }
            }
        }

        return cells;
    }

    //fill nulls with pathpieces, and init the paths of the nodes
    private static void initPaths(Cell[][] nodes, boolean[][] cellData) {

    }

    //makes, fills and returns a path.
    private static void initNodePaths(Node node, Cell[][] cells, boolean[][] cellData) {
        
    }

    private static Path makeNodePath(Node node, Cell[][] cells, boolean[][] cellData, Direction direction) {
        Node startNode = node;
        HashMap<int[], Direction> pathPieceLocations = new HashMap<>();

        Direction prevDirection = direction;
        int curX = node.getPositionX();
        int curY = node.getPositionY();
       Node endNode = null;

        while (endNode == null) {
            switch (direction) {
                case NORTH:
                    curY++;
                    int[] arr1 = {curX, curY};

                    if (cells[curX][curY] != null && cells[curX][curY] instanceof Node) {
                        endNode = (Node) cells[curX][curY];

                    } else {
                        pathPieceLocations.put(arr1, direction);
                    }
                    break;
                    
                case SOUTH:
                    curY--;
                    int[] arr2 = {curX, curY};
                    if (cells[curX][curY] != null && cells[curX][curY] instanceof Node) {
                        endNode = (Node) cells[curX][curY];

                    } else {
                        pathPieceLocations.put(arr2, direction);
                    }
                    break;
                    
                case EAST:
                    int[] arr3 = {curX, curY};
                    curX++;
                    if (cells[curX][curY] != null && cells[curX][curY] instanceof Node) {
                        endNode = (Node) cells[curX][curY];
                    } else {
                        pathPieceLocations.put(arr3, direction);
                    }
                    break;
                    
                case WEST:
                    curX--;
                    int[] arr4 = {curX, curY};
                    if (cells[curX][curY] != null && cells[curX][curY] instanceof Node) {
                        endNode = (Node) cells[curX][curY];
                    } else {
                        pathPieceLocations.put(arr4, direction);
                    }
                    break;
            }
            
            if(endNode == null){
                
            }
        }
        
        return new Path(node, endNode, null);
    }

    private static PathPiece makePathPiece(int x, int y, Direction prevDirection, boolean[][] cellData, Path path) {
        ArrayList<Direction> possDirections = getNextDirections(x, y, cellData, prevDirection);
        if (possDirections.size() > 1) {
            return null;
        } else {
            return new PathPiece(x, y, path, possDirections.get(0), prevDirection);
        }
    }

    private static Direction getNextDirection(PathPiece pathPiece, boolean[][] cellData, Direction prevDirection) {
        for (Direction direction : Direction.values()) {
            if (direction != prevDirection) {
                try {
                    switch (direction) {
                        case NORTH:
                            if (cellData[pathPiece.getPositionX()][pathPiece.getPositionY() + 1]) {
                                return direction;
                            }
                            break;

                        case SOUTH:
                            if (cellData[pathPiece.getPositionX()][pathPiece.getPositionY() - 1]) {
                                return direction;
                            }
                            break;

                        case EAST:
                            if (cellData[pathPiece.getPositionX() + 1][pathPiece.getPositionY()]) {
                                return direction;
                            }
                            break;
                        case WEST:
                            if (cellData[pathPiece.getPositionX() - 1][pathPiece.getPositionY()]) {
                                return direction;
                            }
                            break;

                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
        return null;
    }

    private static ArrayList<Direction> getNextDirections(int x, int y, boolean[][] cellData, Direction prevDirection) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (direction != prevDirection) {
                try {
                    switch (direction) {
                        case NORTH:
                            if (cellData[x][y + 1]) {
                                directions.add(direction);
                            }
                            break;

                        case SOUTH:
                            if (cellData[x][y - 1]) {
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
