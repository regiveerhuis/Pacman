/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import Model.Direction;
import Model.GameElement.MovingElement;
import MapData.Tile;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Regi
 */
public class Node extends TraversableCell implements Guider {

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
    
    public Direction getPathDirection(Path path) {
        Direction pathDirection = null;
        for(Direction direction : paths.keySet()) {
            if(paths.get(direction) == path) {
                pathDirection = direction;
            }
        }
        
        return pathDirection;
    }

    public void setPath(Direction direction, Path path) {
        paths.put(direction, path);
    }

    public Collection<Path> getPaths() {
        return paths.values();
    }

    private boolean isPathValid(Direction direction) {
        return paths.containsKey(direction) && paths.get(direction) != null;
    }

    @Override
    public String toString() {
        String r = "node";
        for (Direction d : paths.keySet()) {
            r += ", " + d.toString().charAt(0);
        }
        return r;
    }

    //makes, fills and returns a path.
    public void initNode(Cell[][] cells, Tile[][] cellData) {
        for (Direction direction : getPossibleDirections()) {

            if (!isPathValid(direction)) {
                makeNodePath(cells, cellData, direction);
            }
        }
    }

    private void makeNodePath(Cell[][] cells, Tile[][] cellData, Direction direction) {
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

        ArrayList<TraversableCell> pathPieces = new ArrayList();

        Path path = new Path(pathPieces);
        endNode.setPath(prevDirection.inverse(), path);
        setPath(direction, path);

        while (pathPieceLocations.size() != 0) {
            int[] locations = pathPieceLocations.pop();
            Direction[] directions = pathPieceDirections.pop();
            PathPiece pathPiece = new PathPiece(locations[0], locations[1], path, directions[0], directions[1]);

            pathPieces.add(pathPiece);
            cells[pathPiece.getPositionX()][pathPiece.getPositionY()] = pathPiece;
        }
        pathPieces.add(this);
        pathPieces.add(0, endNode);
    }

    private ArrayList<Direction> getNextDirections(int x, int y, Tile[][] cellData, Direction prevDirection) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        try {

            if (cellData[x][y - 1] != Tile.WALL) {
                directions.add(Direction.NORTH);
            }

            if (cellData[x][y + 1] != Tile.WALL) {
                directions.add(Direction.SOUTH);
            }

            if (cellData[x + 1][y] != Tile.WALL) {
                directions.add(Direction.EAST);
            }

            if (cellData[x - 1][y] != Tile.WALL) {
                directions.add(Direction.WEST);
            }

        } catch (IndexOutOfBoundsException e) {

        }

        directions.remove(prevDirection.inverse());
        return directions;
    }

    @Override
    public void tryMove(Direction direction, MovingElement movingElement) {
        if (isPossibleDirection(direction)) {
            TraversableCell cell;
            Path path = paths.get(direction);

            if (path.isEndNode(this)) {
                cell = path.getPreviousTraversableCell(this);
            } else {
                cell = path.getNextTraversableCell(this);
            }
            if (movingElement.getGuider() == this) {
                if (cell instanceof PathPiece) {
                    movingElement.setGuider(new PathGuide(path, (PathPiece) cell));
                }
            }
            this.removeElement(movingElement);
            cell.addMover(movingElement);

        }
    }

    @Override
    public void addMover(MovingElement movingElement) {
        movingElement.setGuider(this);
        super.addMover(movingElement);
    }

    @Override
    public TraversableCell getCurrentCell() {
        return this;
    }

    @Override
    public ArrayList<Node> getClosestNodes() {
        ArrayList<Node> n = new ArrayList();
        n.add(this);
        return n;
    }

    @Override
    public boolean isPossibleDirection(Direction direction) {
        return paths.containsKey(direction);
    }

    @Override
    public Guider guiderClone() {
        return this;
    }

    @Override
    public void removeMover(MovingElement movingElement) {
        removeElement(movingElement);
    }

    @Override
    public int distanceToNode(Node node) {
        if (node == this) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public Direction getDirectionOfNode(Node targetNode) {
        for (Direction dir : paths.keySet()) {
            Path path = paths.get(dir);
            if (path.isStartNode(this)) {
                if (targetNode == path.getEndNode()) {
                    return dir;
                }
            } else {
                if (targetNode == path.getStartNode()) {
                    return dir;
                }
            }
        }
        return null;
    }
}
