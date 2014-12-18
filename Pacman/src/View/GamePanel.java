/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Regi
 */
public class GamePanel extends JPanel {

    private GameFrame gameFrame;
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(gameFrame == null){
            gameFrame = (GameFrame) SwingUtilities.getAncestorOfClass(GameFrame.class, this);
        }
        gameFrame.drawGame(g);
    }
    
}
