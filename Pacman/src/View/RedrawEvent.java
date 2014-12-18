/*
 * 
 */

package View;

import java.awt.Event;
import java.awt.Graphics;

/**
 * @author Matthias
 */
public class RedrawEvent extends Event{
    public Graphics g;

    public RedrawEvent(Object o, int i, Object o1) {
        super(o, i, o1);
    }
}
