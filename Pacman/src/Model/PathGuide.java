/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Regi
 */
public class PathGuide implements Guider {

    private PathPiece pathPiece;
    private Path path;

    public PathGuide(Path path, PathPiece pathPiece) {
        this.path = path;
        this.pathPiece = pathPiece;
    }

    @Override
    public void tryMove(Direction direction, MovingElement movingElement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TraversableCell getCurrentCell() {
        return pathPiece;
    }

    @Override
    public List<Node> getClosestNodes() {
        ArrayList<Node> n = new ArrayList();
        return n;
    }

    @Override
    public boolean isPossibleDirection(Direction direction) {
        return pathPiece.isPossibleDirection(direction);
    }

}
