/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MapData;

/**
 *
 * @author Regi
 */
public enum Tile {
    WALL,
    EMPTY,
    PACMAN,
    GHOST,
    GHOST_ONLY,
    SUPER_DOT
    ;

    
    
    public static Tile fromString(String value) {
        for(Tile tile : Tile.values()){
            if(value.equals(tile.name())){
                return tile;
            }
        }
        return null;
    }
    
}
