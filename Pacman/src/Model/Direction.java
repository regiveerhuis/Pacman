/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Regi
 */
public enum Direction {

    NORTH,
    SOUTH,
    EAST,
    WEST;

    
    public Direction inverse()
    {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return null;
        }
    }
    
    public int toDegrees(){
                switch (this) {
            case NORTH:
                return 90;
            case SOUTH:
                return 270;
            case EAST:
                return 0;
            case WEST:
                return 180;
            default:
                return 0;
        }
    }
}
