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
        System.out.println(paths.keySet());
        return paths.keySet().toArray(new Direction[paths.keySet().size()]);
    }

    public void setPath(Direction direction, Path path) {
        paths.put(direction, path);
    }

    public boolean pathValid(Direction direction){
        return paths.containsKey(direction) && paths.get(direction) != null;
    }
   
    @Override
    public String toString(){
        String r = "node"; 
        for(Direction d : paths.keySet()){
           r+= ", " + d.toString().charAt(0);
        }
        return r;
    }
}
