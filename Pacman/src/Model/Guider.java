/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.List;

/**
 *
 * @author Regi
 */
public interface Guider {
    
    public void tryMove(Direction direction, MovingElement movingElement);
    
    public TraversableCell getCurrentCell();
    
    public List<Node> getClosestNodes();
    
    public boolean isPossibleDirection(Direction direction);
}
