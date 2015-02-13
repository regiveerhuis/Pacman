package ResearchTests;


import Model.Cell.Guider;
import Model.GameElement.PathingTarget;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Regi
 */
public class TargetWrapper implements PathingTarget {

    private Guider guider;
    
    public TargetWrapper(Guider guider){
        this.guider = guider;
    }
    
    @Override
    public boolean moveTo() {
        return true;
    }

    @Override
    public Guider getGuider() {
        return guider;
    }
    
}
