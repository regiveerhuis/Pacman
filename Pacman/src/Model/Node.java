/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Regi
 */
public class Node extends TraversableCell{
    private Map<Direction, Path> paths;
    
    public Node(int positionX, int positionY, Map<Direction, Path> paths) {
        super(positionX, positionY);
        this.paths = paths;
    }

    /**
     * @return the paths
     */
    public Direction[] getPossibleDirections() {
        return (Direction[]) paths.keySet().toArray();
    }
    
   
}
