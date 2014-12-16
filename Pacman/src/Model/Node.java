/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Regi
 */
public class Node extends TraversableCell{
    private Map<Direction, Path> paths;
    
    public Node(int positionX, int positionY, Map<Direction, Path> paths) {
        super(positionX, positionY);
        this.paths = paths;
    }
    
    /**
     * @return the paths
     */
    @Override
    public Direction[] getPossibleDirections() {
        return paths.keySet().toArray(new Direction[paths.keySet().size()]);
    }

    @Override
    public TraversableCell tryMove(Direction direction){
        if(paths.containsKey(direction)){
            if(paths.get(direction).isStartNode(this)){
                return paths.get(direction).getNextTraversableCell(this);
            }else if(paths.get(direction).isEndNode(this)){
                return paths.get(direction).getPreviousTraversableCell(this);
            }
        }
        return null;
    }
    
    public void setPath(Direction direction, Path path) {
        paths.put(direction, path);
    }

    public boolean pathValid(Direction direction){
        return paths.containsKey(direction) && paths.get(direction) != null;
    }
    
    @Override
    public String toString(){
        String r = "node"; 
        for(Direction d : paths.keySet()){
           r+= ", " + d.toString().charAt(0);
        }
        return r;
    }
    
     //makes, fills and returns a path.
    public void initNode(Cell[][] cells, boolean[][] cellData) {
        for (Direction direction : getPossibleDirections()) {

            if (!pathValid(direction)) {
                makeNodePath(cells, cellData, direction);
            }
        }
    }
    
    private void makeNodePath(Cell[][] cells, boolean[][] cellData, Direction direction) {
        Stack<int[]> pathPieceLocations = new Stack<>();
        Stack<Direction[]> pathPieceDirections = new Stack<>();

        Direction curDirection = direction;
        Direction prevDirection = direction;
        int curX = getPositionX();
        int curY = getPositionY();
        Node endNode = null;

        while (endNode == null) {
            switch (curDirection) {
                case NORTH:
                    curY--;
                    break;

                case SOUTH:
                    curY++;
                    break;

                case EAST:
                    curX++;
                    break;

                case WEST:
                    curX--;
                    break;
            }

            int[] arr = {curX, curY};
            prevDirection = curDirection;
            if (cells[curX][curY] != null && cells[curX][curY] instanceof Node) {
                endNode = (Node) cells[curX][curY];

            } else {
                curDirection = getNextDirections(curX, curY, cellData, curDirection).get(0);
                Direction[] dir = {prevDirection.inverse(), curDirection};
                pathPieceLocations.add(arr);
                pathPieceDirections.add(dir);
            }
        }

        ArrayList<PathPiece> pathPieces = new ArrayList();
        Path path = new Path(endNode, this, pathPieces);
        endNode.setPath(prevDirection.inverse(), path);
        setPath(direction, path);
        
        while (pathPieceLocations.size() != 0) {
            int[] locations = pathPieceLocations.pop();
            Direction[] directions = pathPieceDirections.pop();
            PathPiece pathPiece = new PathPiece(locations[0], locations[1], path, directions[0], directions[1]);

            pathPieces.add(pathPiece);
            cells[pathPiece.getPositionX()][pathPiece.getPositionY()] = pathPiece;
        }
    }
    
    private ArrayList<Direction> getNextDirections(int x, int y, boolean[][] cellData, Direction prevDirection) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (direction.inverse() != prevDirection) {
                try {
                    switch (direction) {
                        case NORTH:
                            if (cellData[x][y - 1]) {
                                directions.add(direction);
                            }
                            break;

                        case SOUTH:
                            if (cellData[x][y + 1]) {
                                directions.add(direction);
                            }
                            break;

                        case EAST:
                            if (cellData[x + 1][y]) {
                                directions.add(direction);
                            }
                            break;
                        case WEST:
                            if (cellData[x - 1][y]) {
                                directions.add(direction);
                            }
                            break;

                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
        return directions;
    }
}
