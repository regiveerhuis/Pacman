/*
 * 
 */

package Model;

import View.RedrawEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.RedrawListener;

/**
 * @author Matthias
 */
public abstract class MovingElement extends GameElement implements ActionListener, RedrawListener {
    
    @Override
    public abstract void actionPerformed (ActionEvent e);
    
    public abstract void move (Direction d);
    
}
