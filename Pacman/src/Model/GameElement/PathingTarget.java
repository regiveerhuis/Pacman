/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

import Model.Cell.Guider;

/**
 *
 * @author Regi
 */
public interface PathingTarget {
    public boolean moveTo();
    public Guider getGuider();
}
