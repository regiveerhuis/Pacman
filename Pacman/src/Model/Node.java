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
    @Override
    public Direction[] getPossibleDirections() {
        return paths.keySet().toArray(new Direction[paths.keySet().size()]);
    }

    @Override
    public TraversableCell tryMove(Direction direction){
        if(paths.containsKey(direction)){
            if(paths.get(direction).isStartNode(this)){
                return paths.get(direction).getNextTraversableCell(this);
            }else if(paths.get(direction).isEndNode(this)){
                return paths.get(direction).getPreviousTraversableCell(this);
            }
        }
        return null;
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
