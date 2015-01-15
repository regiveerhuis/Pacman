/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Regi
 */
public class Path {

    private List<TraversableCell> cells;

    public Path(List<TraversableCell> cells) {
        this.cells = cells;
    }

    public TraversableCell getNextTraversableCell(TraversableCell cell) {
        int index = cells.indexOf(cell);
        return cells.get(index + 1);
    }

    public TraversableCell getPreviousTraversableCell(TraversableCell cell) {
        int index = cells.indexOf(cell);
        return cells.get(index - 1);
    }

    public boolean isStartNode(Node node) {
        return node == cells.get(0);
    }

    public boolean isEndNode(Node node) {
        return node == cells.get(cells.size()-1);
    }
    
    public boolean containsCell(TraversableCell traversableCell){
        return cells.contains(traversableCell);
    }

    public Node getStartNode() {
        return (Node) cells.get(0);
    }

    public Node getEndNode() {
        return (Node) cells.get(cells.size()-1);
    }

    public int getLength() {
        return cells.size();
    }

    public int distanceToStart(TraversableCell startCell) {
        return cells.indexOf(startCell)+1;
    }
}
