/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.Cell.Cell;
import Model.Direction;
import Model.GameElement.Ghost;
import Model.Cell.Node;
import Model.GameElement.Pacman;
import Model.Cell.Path;
import Model.Cell.PathPiece;
import Model.PlayGround;
import Model.Cell.TraversableCell;
import Model.Cell.Wall;
import java.awt.Color;
import java.util.Map;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Stack;

/**
 *
 * @author Regi
 */
public class LevelData {

    private static Map<Level, LevelData> levels = new EnumMap<Level, LevelData>(Level.class);
    
    //Level1
    static {
        boolean[][] levelMap = {
            //       0      1      2      3      4        5       6       7       8       9       10      11      12      13      
            /*0*/{false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            /*1*/ {false, true, true, true, true, true, false, true, false, true, true, false, true, false},
            /*2*/ {false, true, false, true, false, true, true, true, true, false, true, true, true, false},
            /*3*/ {false, true, true, true, false, true, false, false, true, true, true, false, true, false},
            /*4*/ {false, true, false, true, false, true, true, true, false, false, true, false, true, false},
            /*5*/ {false, true, true, true, true, true, false, true, true, true, true, true, true, false},
            /*6*/ {false, false, false, false, false, false, false, false, false, false, false, false, false, false}
        };

        boolean[][] transposedMap = new boolean[levelMap[0].length][levelMap.length];
        for (int i = 0; i < levelMap.length; i++) {
            for (int j = 0; j < levelMap[0].length; j++) {
                transposedMap[j][i] = levelMap[i][j];
            }
        }

        int[] startPosGhostX = {12, 12, 12, 12};
        int[] startPosGhostY = {1, 1, 1, 1};
        int[][] ghostPieces = {{12, 1}};
        int[][] superDotLocations = {{1, 5}, {5, 3}, {11, 5}, {9, 3}};
        levels.put(Level.Level1, new LevelData(transposedMap, 1, 1, startPosGhostX, startPosGhostY, ghostPieces, superDotLocations));
    }
    
    //level2
    static{
         boolean[][] levelMap = {
            //     0        1       2       3       4       5       6       7       8       9       10      11      12      13      
             /*0*/ {false,false,false,false,false,false,          false,false,false,false,false,false,false,false},
             /*1*/ {false, true, true, true, false,true,          true, true, false,true, true, true, true, false},
             /*2*/ {false, true, false,true, true, true,          false,true, true, true, false,false,true, false},
             /*3*/ {false, true, false,false,true, false,         true, false,true, false,true, true, true, false},
             /*4*/ {false, false,true, false,true, false,         true, false,true, true, true, false,true, false},
             /*5*/ {false, true, true, true, true, false,         true, true, true, false,true, true, true, false},
             /*6*/ {false, false,true, false,true, false,         true, false,true, true, true, false,true, false},
             /*7*/ {false, true, false,false,true, false,         true, false,true, false,true, true, true, false},
             /*8*/ {false, true, false,true, true, true,          false,true, true, true, false,false,true, false},
             /*9*/ {false, true, true, true, false,true,          true, true, false,true, true, true, true, false},
             /*10*/{false,false,false,false,false,false,          false,false,false,false,false,false,false,false},
        };

        boolean[][] transposedMap = new boolean[levelMap[0].length][levelMap.length];
        for (int i = 0; i < levelMap.length; i++) {
            for (int j = 0; j < levelMap[0].length; j++) {
                transposedMap[j][i] = levelMap[i][j];
            }
        }

        int[] startPosGhostX = {6, 6, 6, 6};
        int[] startPosGhostY = {3, 4, 6, 7};
        int[][] ghostPieces = {{6, 3},{6,4},{6,5},{6,6},{6,7},{7,5}};
        int[][] superDotLocations = {{1, 3}, {1, 7}, {8, 5}, {12, 5}};
        levels.put(Level.Level2, new LevelData(transposedMap, 4, 5, startPosGhostX, startPosGhostY, ghostPieces, superDotLocations));
    }

    //level3
        static{
        boolean[][] levelMap = {
            //       0      1      2      3      4        5       6       7       8       9       10      11      12      13      
            /*0*/{false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            /*1*/ {false, true, true, true, true, true, false, true, false, true, true, false, true, false},
            /*2*/ {false, true, false, true, false, true, true, true, true, false, true, true, true, false},
            /*3*/ {false, true, true, true, false, true, false, false, true, true, true, false, true, false},
            /*4*/ {false, true, false, true, false, true, true, true, false, false, true, false, true, false},
            /*5*/ {false, true, true, true, true, true, false, true, true, true, true, true, true, false},
            /*6*/ {false, false, false, false, false, false, false, false, false, false, false, false, false, false}
        };

        boolean[][] transposedMap = new boolean[levelMap[0].length][levelMap.length];
        for (int i = 0; i < levelMap.length; i++) {
            for (int j = 0; j < levelMap[0].length; j++) {
                transposedMap[j][i] = levelMap[i][j];
            }
        }

        int[] startPosGhostX = {12, 12, 12, 12};
        int[] startPosGhostY = {1, 1, 1, 1};
        int[][] ghostPieces = {{12, 1}};
        int[][] superDotLocations = {{1, 5}, {5, 3}, {11, 5}, {9, 3}};
        levels.put(Level.Level1, new LevelData(transposedMap, 1, 1, startPosGhostX, startPosGhostY, ghostPieces, superDotLocations));
    
    
    
    }

    private boolean[][] cellData;
    private int startPositionPacmanX;
    private int startPositionPacmanY;
    private int[] startPositionGhostX;
    private int[] startPositionGhostY;
    private int[][] ghostPieces;
    private int[][] superDotLocations;

    private LevelData(boolean[][] cellData, int startPacmanX, int startPacmanY, int[] startGhostX, int[] startGhostY, int[][] ghostPieces, int[][] superDotLocations) {
        this.cellData = cellData;
        this.startPositionGhostX = startGhostX;
        this.startPositionGhostY = startGhostY;
        this.startPositionPacmanX = startPacmanX;
        this.startPositionPacmanY = startPacmanY;
        this.ghostPieces = ghostPieces;
        this.superDotLocations = superDotLocations;
    }

    public boolean[][] getCellData() {
        return cellData;
    }

    public static LevelData getLevelData(Level level) {
        if (levels.containsKey(level)) {
            return levels.get(level);
        } else {
            throw new IllegalArgumentException("Level does not exist");
        }
    }

    /**
     * @return the startPositionPacmanX
     */
    public int getStartPositionPacmanX() {
        return startPositionPacmanX;
    }

    /**
     * @return the startPositionPacmanY
     */
    public int getStartPositionPacmanY() {
        return startPositionPacmanY;
    }

    /**
     * @return the startPositionGhostX
     */
    public int[] getStartPositionGhostX() {
        return startPositionGhostX;
    }

    /**
     * @return the startPositionGhostY
     */
    public int[] getStartPositionGhostY() {
        return startPositionGhostY;
    }

    public int[][] getGhostPieces() {
        return ghostPieces;
    }

    public int[][] getSuperDotLocations() {
        return superDotLocations;
    }

    public boolean isGhostPiece(int x, int y) {
        for (int[] pos : ghostPieces) {
            if (x == pos[0] && y == pos[1]) {
                return true;
            }
        }
        return false;
    }
}
