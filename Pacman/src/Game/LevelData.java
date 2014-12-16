/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.Cell;
import Model.Direction;
import Model.Ghost;
import Model.Node;
import Model.Pacman;
import Model.Path;
import Model.PathPiece;
import Model.PlayGround;
import Model.TraversableCell;
import Model.Wall;
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

    static {
        boolean[][] levelMap = {
            //       0      1      2      3      4        5       6       7       8       9       10      11      12      13      
            /*0*/{false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            /*1*/ {false, true, true, true, true, true, false, true, false, true, true, true, true, false},
            /*2*/ {false, true, false, true, false, true, true, true, true, false, true, false, true, false},
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

        int[] startPosGhostX = {4, 9, 9, 12};
        int[] startPosGhostY = {5, 5, 1, 3};
        levels.put(Level.Level, new LevelData(transposedMap, 1, 1, startPosGhostX, startPosGhostY));
    }

    private boolean[][] cellData;
    private int startPositionPacmanX;
    private int startPositionPacmanY;
    private int[] startPositionGhostX;
    private int[] startPositionGhostY;

    private LevelData(boolean[][] cellData, int startPacmanX, int startPacmanY, int[] startGhostX, int[] startGhostY) {
        this.cellData = cellData;
        this.startPositionGhostX = startGhostX;
        this.startPositionGhostY = startGhostY;
        this.startPositionPacmanX = startPacmanX;
        this.startPositionPacmanY = startPacmanY;
    }
    
    public boolean[][] getCellData(){
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

}
