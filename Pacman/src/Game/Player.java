/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author Regi
 */
public class Player {

    private int lives;
    private int points;

    public Player(int lives) {
        points = 0;
        this.lives = lives;
    }

    public void pacmanDies() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
