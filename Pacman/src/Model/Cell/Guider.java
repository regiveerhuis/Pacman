/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Cell;

import Model.Direction;
import Model.GameElement.MovingElement;
import java.util.ArrayList;

/**
 *
 * @author Regi
 */
public interface Guider {
    
    public void tryMove(Direction direction, MovingElement movingElement);
    
    public void removeMover(MovingElement movingElement);
    
    public TraversableCell getCurrentCell();
    
    public ArrayList<Node> getClosestNodes();
    
    public int distanceToNode(Node node);
    
    public boolean isPossibleDirection(Direction direction);

    public Guider guiderClone();

    public Direction[] getPossibleDirections();

    public Direction getDirectionOfNode(Node targetNode);

    
}
