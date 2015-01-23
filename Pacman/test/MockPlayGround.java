/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import MapData.Tile;
import Model.Cell.Cell;
import Model.Cell.Wall;
import Model.Cell.Node;
import Model.Cell.Path;
import MapData.LevelData;
import Model.Cell.GhostNode;
import Model.Direction;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;

/**
 *
 * @author Regi
 */
public class MockPlayGround {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Regi
 */


    private final Cell[][] cells;

    public int getSize(){
        return cells.length;
    }
    
    public MockPlayGround(LevelData levelData) {
        Tile[][] cellData = levelData.getCellData();
        cells = new Cell[cellData.length][cellData[0].length];
        createNodes(levelData);
    }

    public Node getNodeAt(int x, int y){
        System.out.println(cells[x][y].getClass().toString());
        if(cells[x][y] instanceof Node){
            return (Node) cells[x][y];
        }
        return null;
    }
    
    //fills the Cell[][] array with walls and nodes
    private void createNodes(LevelData levelData) {
        Tile[][] cellData = levelData.getCellData();
        for (int i = 0; i < cellData.length; i++) {
            for (int j = 0; j < cellData[i].length; j++) {
                if (cellData[i][j] == Tile.WALL) {
                    cells[i][j] = new Wall(i, j);
                } else {
                    boolean isGhostNode = false;
                    ArrayList<Direction> ghostDirs = new ArrayList();
                    EnumMap<Direction, Path> directions = new EnumMap<Direction, Path>(Direction.class);
                    //if you're not on the far left and the cell to the left of you is not a wall, add a neighbour
                    if (i > 0 && cellData[i - 1][j] != Tile.WALL) {
                        directions.put(Direction.WEST, null);
                        if (levelData.isGhostPiece(i - 1, j)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.WEST);
                        }
                    }

                    //if you're not on the far right and the cell to the right of you is not a wall, add a neighbour
                    if (i + 1 < cellData.length && cellData[i + 1][j] != Tile.WALL) {
                        directions.put(Direction.EAST, null);
                        if (levelData.isGhostPiece(i + 1, j)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.EAST);
                        }
                    }

                    //if you're not at the top and the cell above you is not a wall, add a neighbour
                    if (j > 0 && cellData[i][j - 1] != Tile.WALL) {
                        directions.put(Direction.NORTH, null);
                        if (levelData.isGhostPiece(i, j - 1)) {
                            isGhostNode = true;
                            ghostDirs.add(Direction.NORTH);
                        }
                    }

                    //if you're not at the bottom and the cell under you is not a wall, add a neighbour
                    if (j + 1 < cellData[i].length && cellData[i][j + 1] != Tile.WALL) {
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
        HashSet paths = new HashSet();
        for(Node node:getNodes()){
            paths.addAll(node.getPaths());
        }
        return paths;
    }


}
