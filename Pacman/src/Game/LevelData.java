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
    private static void initPaths(Cell[][] nodes, boolean[][] cellData){
        
        
    }
    
    //makes, fills and returns a path.
    private static Path initNodePaths(Node node, Cell[][] cells, boolean[][] cellData){
        return null;
    }
}
