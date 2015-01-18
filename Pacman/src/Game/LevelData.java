/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Model.Tile;
import java.util.ArrayList;

/**
 *
 * @author Regi
 */
public class LevelData {
    
    private final Tile[][] cellData;
    
    public LevelData(Tile[][] cellData) {
        this.cellData = cellData;
    }

    public Tile[][] getCellData() {
        return cellData;
    }

    public ArrayList<int[]> getIndexesOfType(Tile tile){
        ArrayList<int[]> indexes = new ArrayList();
        for(int x = 0;x<cellData.length;x++){
            for(int y = 0; y<cellData[x].length;y++){
                if(cellData[x][y] == tile || (cellData[x][y] == Tile.GHOST && tile == Tile.GHOST_ONLY)){
                    indexes.add( new int[]{x,y} );
                }
            }
        }
        return indexes;
    }
    
    public boolean isGhostPiece(int x, int y) {
        return cellData[x][y] == Tile.GHOST_ONLY || cellData[x][y] == Tile.GHOST;
    }
}
