/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.Observable;

/**
 *
 * @author Regi
 */
public class Player extends Observable{

    private int lives;
    private int points;

    public Player(int lives) {
        points = 0;
        this.lives = lives;
    }

    public void pacmanDies() {
        lives--;
        setChanged();
        notifyObservers(lives);
    }

    public int getLives() {
        return lives;
    }

    public void addPoints(int points) {
        if(points!=0){
            this.points += points;
            setChanged();
            notifyObservers(lives);
        }
    }

    public int getPoints() {
        return points;
    }
}
