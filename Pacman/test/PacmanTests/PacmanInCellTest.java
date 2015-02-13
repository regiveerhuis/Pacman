package PacmanTests;

import Game.Player;
import Model.Cell.Node;
import Model.Cell.Path;
import Model.Cell.PathPiece;
import Model.Cell.TraversableCell;
import Model.Direction;
import Model.GameElement.Ghost;
import Model.GameElement.PacDot;
import Model.GameElement.Pacman;
import Model.GameElement.RandomGhost;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Assert.*;
import static junit.framework.Assert.*;
import org.junit.Test;

/*
 * 
 */

/**
 * @author Matthias
 */
public class PacmanInCellTest {
    
    @Before
    public void setUp() {
        
    }
    
    //Pacman not invincible, no pacdot, no ghost
    @Test
    public void testAction1() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player still has 3 lives and 0 points
        assertEquals(3, player.getLives()); //No lives lost
        assertEquals(0, player.getPoints()); //No points added
    }
    
    //Pacman not invincible, pacdot in cell, no ghost
    @Test
    public void testAction2() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Add a pacdot to the testCell
        PacDot pacdot = new PacDot();
        testCell.addStatic(pacdot);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player still has 3 lives and 10 points were added
        assertEquals(3, player.getLives()); //No lives lost
        assertEquals(10, player.getPoints()); //One pacdot equals 10 points
    }
    
    //Pacman not invincible, no pacdot, ghost in cell
    @Test
    public void testAction3() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Add a ghost to the testCell
        Node ghostStartCell = new Node(0, 1, paths);
        Ghost ghost = new RandomGhost(null, ghostStartCell, null, 0);
        ghost.setGuider(testCell);
        testCell.addMover(ghost);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player lost 1 live and has 0 points
        assertEquals(2, player.getLives()); //One live lost
        assertEquals(0, player.getPoints()); //No points earned
    }
    
    //Pacman not invincible, pacdot and ghost in cell
    @Test
    public void testAction4() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Add a pacdot to the testCell
        PacDot pacdot = new PacDot();
        testCell.addStatic(pacdot);
        
        //Add a ghost to the testCell
        Node ghostStartCell = new Node(0, 1, paths);
        Ghost ghost = new RandomGhost(null, ghostStartCell, null, 0);
        ghost.setGuider(testCell);
        testCell.addMover(ghost);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player lost 1 live and has 10 points
        assertEquals(2, player.getLives()); //One live lost
        assertEquals(10, player.getPoints()); //One pacdot equals 10 points
    }
    
    //Pacman invincible, no pacdot, no ghost
    @Test
    public void testAction5() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player and make him invincible
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        pacman.becomeInvincible();
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player still has 3 lives and 0 points
        assertEquals(3, player.getLives()); //No lives lost
        assertEquals(0, player.getPoints()); //No points added
    }
    
    //Pacman invincible, pacdot in cell, no ghost
    @Test
    public void testAction6() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Add a pacdot to the testCell
        PacDot pacdot = new PacDot();
        testCell.addStatic(pacdot);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        pacman.becomeInvincible();
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player still has 3 lives and 10 points were added
        assertEquals(3, player.getLives()); //No lives lost
        assertEquals(10, player.getPoints()); //One pacdot equals 10 points
    }
    
    //Pacman invincible, no pacdot, ghost in cell
    @Test
    public void testAction7() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Add a ghost to the testCell
        Node ghostStartCell = new Node(0, 1, paths);
        Ghost ghost = new RandomGhost(null, ghostStartCell, null, 0);
        ghost.setGuider(testCell);
        testCell.addMover(ghost);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        pacman.becomeInvincible();
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player lost no lives and has 200 points
        assertEquals(3, player.getLives()); //No lives lost
        assertEquals(200, player.getPoints()); //200 points added for ghost
    }
    
    //Pacman invincible, pacdot and ghost in cell
    @Test
    public void testAction8() {
        //Make a startCell for Pacman to spawn in
        Map<Direction, Path> paths = new HashMap();
        Node startCell = new Node(0, 0, paths);
        //Make a testcell, there are no elements in this cell for this test case
        Node testCell = new Node(1, 0, paths);
        
        //Add a pacdot to the testCell
        PacDot pacdot = new PacDot();
        testCell.addStatic(pacdot);
        
        //Add a ghost to the testCell
        Node ghostStartCell = new Node(0, 1, paths);
        Ghost ghost = new RandomGhost(null, ghostStartCell, null, 0);
        ghost.setGuider(testCell);
        testCell.addMover(ghost);
        
        //Make a player with 3 lives
        //If Pacman lives, 3 lives remain
        //If Pacman dies, 1 lives gets subtracted
        //If Pacman eats nothing, no points are added
        //If Pacman eats a Pacdot, 10 points are added
        //If Pacman eats a Ghost, 200 points are added
        Player player = new Player(3);
        
        //Make Pacman, give it the startCell and the player
        Pacman pacman = new Pacman(startCell, player);
        pacman.setGuider(startCell);
        pacman.becomeInvincible();
        
        //Put Pacman in the testCell
        testCell.addMover(pacman);
        
        //Check if the player lost no lives and has 210 points
        assertEquals(3, player.getLives()); //No lives lost
        assertEquals(210, player.getPoints()); //210 points added for ghost + pacdot
    }
}
