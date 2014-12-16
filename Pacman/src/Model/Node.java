/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.awt.Graphics;
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
    public void draw(Graphics g){
        super.draw(g);
        Direction[] dirs = getPossibleDirections();
        int i = 0;
        g.translate(positionX * CELL_SIZE, positionY * CELL_SIZE);
        for(Direction dir : dirs){
            g.drawString(dir.toString(), 0, i*8);
            i++;
        }
        g.translate(-positionX* CELL_SIZE, -positionY* CELL_SIZE);
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
