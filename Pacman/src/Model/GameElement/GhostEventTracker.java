/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.GameElement;

import java.util.WeakHashMap;

/**
 *
 * @author Regi
 */
public class GhostEventTracker{
    WeakHashMap<Ghost, Object> hashMap = new WeakHashMap(); //because WeakHashSet does not exist, WeakHashMap is used instead
    private static GhostEventTracker instance = null;
          
    private GhostEventTracker() {
    }
    
    public void addGhost(Ghost ghost){
        hashMap.put(ghost, this);
    }
    
    public void notifyGhosts(){
        for(Ghost ghost : hashMap.keySet()){
            ghost.getGuider().removeMover(ghost);
            ghost.die();
        }
    } 
 
    public static GhostEventTracker getGhostEventTracker() {
        if (instance == null) {
            instance = new GhostEventTracker();
        }

        return instance;
    }
}
