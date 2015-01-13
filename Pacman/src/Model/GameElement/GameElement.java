/*
 * 
 */

package Model.GameElement;
import java.awt.Graphics;

/**
 * @author Matthias
 */
public abstract class GameElement {
    
    public abstract void draw(Graphics g);
    
    public abstract GameElementDeathEvent moverEnteredCell(MovingElement mover);
    
    public abstract void die();
}

