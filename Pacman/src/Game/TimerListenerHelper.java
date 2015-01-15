/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Regi
 */
public class TimerListenerHelper implements ActionListener {
    private ActionListener listener;
    private int cycle;
    private int curTick = 0;
    
    public TimerListenerHelper(int cycle, ActionListener listener){
        this.cycle = cycle;
        this.listener = listener;
        
    }
    
    public void setCycle(int Cycle){
        if(cycle > 0){
            this.cycle = cycle;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(curTick >= cycle){
            curTick = 0;
            listener.actionPerformed(ae);
        }else{
            curTick++;
        }
    }
}
