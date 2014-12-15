/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Regi
 */
public class Path {

    private Node startNode;
    private Node endNode;
    private List<PathPiece> pathPieces;

    public Path(Node startNode, Node endNode, List<PathPiece> pathPieces) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.pathPieces = pathPieces;
    }

    public TraversableCell getNextTraversableCell(TraversableCell cell) {
        if (pathPieces.size() == 0) {
            if (cell == startNode) {
                return endNode;
            } else {
                return null;
            }
        }

        if (cell == startNode) {
            return pathPieces.get(0);
        } else if (cell == endNode) {
            return null;
        }

        int index = pathPieces.indexOf(cell);
        if (index == pathPieces.size() - 1) {
            return endNode;
        } else {
            return pathPieces.get(index + 1);
        }

    }

    public TraversableCell getPreviousTraversableCell(TraversableCell cell) {
        if (pathPieces.size() == 0) {
            if (cell == endNode) {
                return startNode;
            } else {
                return null;
            }
        }
        if (cell == endNode) {
            return pathPieces.get(pathPieces.size() - 1);

        } else if (cell == startNode) {
            return null;
        }

        int index = pathPieces.indexOf(cell);
        if (index == 0) {
            return startNode;
        } else {
            return pathPieces.get(index - 1);
        }

    }

    public boolean isStartNode(Node node) {
        return startNode == node;
    }

    public boolean isEndNode(Node node) {
        return endNode == node;
    }
}
