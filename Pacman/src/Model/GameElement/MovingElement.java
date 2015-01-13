/*
 * 
 */

package Model.GameElement;

import Model.Direction;
import Model.Cell.Guider;
import View.RedrawEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.RedrawListener;

/**
 * @author Matthias
 */
public abstract class MovingElement extends GameElement implements ActionListener {
    private Guider guider;
    
    @Override
    public abstract void actionPerformed (ActionEvent e);
    
    public void move (Direction d){
        guider.tryMove(d, this);
    }
    
    public Guider getGuider() {
        return guider;
    } 

    public void setGuider(Guider guider) {
       this.guider = guider;
    }
        
}
