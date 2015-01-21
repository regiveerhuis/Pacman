/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapData;

import Model.Direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Regi
 */
public class RandomMapGenerator {

    private int wallBlockSize = 4;
    private int minimumGhostDistance = 1;
    private boolean ghostsTogether = true;
    private double correctChance = 0.6;
    private int superDotAmount = 4;
    private float connectivity;

    public LevelData getMap(int xSize, int ySize, int seed) {
        ArrayList<Vector> nodes = new ArrayList();
        Random rand = new Random(seed);
        Tile[][] tiles = new Tile[xSize - 1][ySize - 1];
        int pacmanX = rand.nextInt(tiles.length);
        int pacmanY = rand.nextInt(tiles[0].length);
        tiles[pacmanX][pacmanY] = Tile.PACMAN;
        nodes.add(new Vector(pacmanX, pacmanY));
        int ghostX = 0;
        int ghostY = 0;

        if (ghostsTogether) {
            int ghostXBefore = -1;
            int ghostXAfter = -1;

            if (pacmanX - minimumGhostDistance > 0) {
                ghostXBefore = rand.nextInt(pacmanX - minimumGhostDistance);
            }

            if (tiles.length - pacmanX - minimumGhostDistance > 0) {
                ghostXAfter = rand.nextInt(tiles.length - pacmanX - minimumGhostDistance) + pacmanX + minimumGhostDistance;
            }
            ghostX = rand.nextBoolean() && ghostXBefore != -1 ? ghostXBefore : ghostXAfter;
            if (ghostXAfter == -1) {
                throw new IllegalStateException("Map Not Large Enough");
            }
            int ghostYBefore = -1;
            int ghostYAfter = -1;
            if (pacmanY - minimumGhostDistance > 0) {
                ghostYBefore = rand.nextInt(pacmanY - minimumGhostDistance);
            }
            if (tiles[0].length - pacmanY - minimumGhostDistance > 0) {
                ghostYAfter = rand.nextInt(tiles[0].length - pacmanY - minimumGhostDistance) + pacmanY + minimumGhostDistance;
            }
            ghostY = rand.nextBoolean() && ghostYBefore != -1 ? ghostYBefore : ghostYAfter;
            if (ghostYAfter == -1) {
                throw new IllegalStateException("Map Not Large Enough");
            }
            Vector ghostBasePosition = new Vector(ghostX, ghostY);
            nodes.add(ghostBasePosition);
            tiles[ghostBasePosition.x][ghostBasePosition.y] = Tile.GHOST;

            ArrayList<Direction> directionsX = new ArrayList();
            ArrayList<Direction> directionsY = new ArrayList();

            if (ghostY > 0) {
                directionsY.add(Direction.NORTH);
            }
            if (ghostY < tiles.length - 1) {
                directionsY.add(Direction.SOUTH);
            }
            if (ghostX > 0) {
                directionsX.add(Direction.WEST);
            }
            if (ghostX < tiles[0].length - 1) {
                directionsX.add(Direction.EAST);
            }

            Direction xDirection = directionsX.get(rand.nextInt(directionsX.size()));
            Direction yDirection = directionsY.get(rand.nextInt(directionsY.size()));
            Vector[] ghostPositions = new Vector[3];

            if (xDirection == Direction.WEST) {
                ghostPositions[0] = new Vector(-1, 0);
                if (yDirection == Direction.NORTH) {
                    ghostPositions[1] = new Vector(0, -1);
                    ghostPositions[2] = new Vector(-1, -1);

                } else {
                    ghostPositions[1] = new Vector(0, 1);
                    ghostPositions[2] = new Vector(-1, 1);
                }
            } else {
                ghostPositions[0] = new Vector(1, 0);
                if (yDirection == Direction.NORTH) {
                    ghostPositions[1] = new Vector(0, -1);
                    ghostPositions[2] = new Vector(1, -1);
                } else {
                    ghostPositions[1] = new Vector(0, -1);
                    ghostPositions[2] = new Vector(1, 1);
                }
            }
            for (Vector relGhostPos : ghostPositions) {
                Vector ghostPos = ghostBasePosition.addVector(relGhostPos);
                tiles[ghostPos.x][ghostPos.y] = Tile.GHOST;
            }
        }

        for (int i = 0; i < 0; i++) {
            boolean notMadeNode = true;
            while (notMadeNode) {
                Vector loc = new Vector(rand.nextInt(tiles.length), rand.nextInt(tiles[0].length));
                if (!nodes.contains(loc)) {
                    nodes.add(loc);
                    tiles[loc.x][loc.y] = Tile.EMPTY;
                    notMadeNode = false;
                }
            }
        }

//        createPath(tiles, nodes, rand);

        tiles = fillWalls(tiles);

        putSuperDots(tiles, superDotAmount, rand);

        for (Tile[] t : tiles) {
            for (Tile tile : t) {
                if (tile != null) {
                    System.out.print(tile.name() + "\t");
                } else {
                    System.out.println("null\t");
                }
            }
            System.out.println("");
        }

        return new LevelData(tiles);
    }

    private void createPaths(Tile[][] tiles, ArrayList<Vector> nodes, Random rand) {
        HashMap<Vector, ArrayList<Vector>> curConnections = new HashMap();
        HashMap<Vector, Integer> wantedConnections = new HashMap();

        Set<Vector> unusedNodes = new HashSet();
        Set<Vector> partialUsed = new HashSet();
        Set<Vector> filled = new HashSet();

        unusedNodes.addAll(nodes);
        partialUsed.add(nodes.get(rand.nextInt(nodes.size())));

        for (Vector vector : nodes) {
            if (rand.nextFloat() < connectivity) {
                wantedConnections.put(vector, 3);
            } else if (rand.nextBoolean()) {
                wantedConnections.put(vector, 4);
            } else {
                wantedConnections.put(vector, 1);
            }
        }

        while (unusedNodes.size() > 0) {
            Vector startVector = getRandomVector(partialUsed, rand);

            
            if (!curConnections.containsKey(startVector)) {
                curConnections.put(startVector, new ArrayList<Vector>());
            }

            Vector targetVector = getRandomVector(unusedNodes, rand);
            
            if (!curConnections.containsKey(targetVector)) {
                curConnections.put(targetVector, new ArrayList<Vector>());
            }
            
            curConnections.get(startVector).add(targetVector);
            curConnections.get(targetVector).add(startVector);
            unusedNodes.remove(targetVector);
            if(curConnections.get(targetVector).size() == wantedConnections.get(targetVector)){
                filled.add(targetVector);
            }
        }
    }

    private Tile[][] fillWalls(Tile[][] tiles) {

        Tile[][] encappedTiles = new Tile[tiles.length + 2][tiles[0].length + 2];
        for (int x = 1; x < encappedTiles.length - 1; x++) {
            for (int y = 1; y < encappedTiles.length - 1; y++) {
                encappedTiles[x][y] = tiles[x - 1][y - 1];
            }
        }

        for (int x = 0; x < encappedTiles.length; x++) {
            for (int y = 0; y < encappedTiles[0].length; y++) {
                if (encappedTiles[x][y] == null) {
                    encappedTiles[x][y] = Tile.WALL;
                }
            }
        }

        return encappedTiles;
    }

    private void putSuperDots(Tile[][] tiles, int superDotAmount, Random rand) {
        ArrayList<Vector> possPositions = new ArrayList<>();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (tiles[x][y] == Tile.EMPTY) {
                    possPositions.add(new Vector(x, y));
                }
            }
        }

        for (int i = 0; i < superDotAmount; i++) {
            if (possPositions.size() < 1) {
                break;
            }
            Vector position = possPositions.get(rand.nextInt(possPositions.size()));
            tiles[position.x][position.y] = Tile.SUPER_DOT;
            possPositions.remove(position);
        }
    }

    private Vector getRandomVector(Set<Vector> set, Random rand) {
        int item = rand.nextInt(set.size());
        int i = 0;
        for (Vector v : set) {
            if (item == i) {
                return v;
            }
            i++;
        }
        return null;
    }

    private class Vector {

        int x;
        int y;

        Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Vector addVector(Vector v1) {
            return new Vector(this.x + v1.x, y + v1.y);
        }

        public Vector getVectorTo(Vector v1) {
            return new Vector(v1.x - this.x, v1.y - this.y);
        }

        @Override
        public String toString() {
            return "[" + String.valueOf(x) + ", " + String.valueOf(y) + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vector)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            return ((Vector) obj).x == this.x && ((Vector) obj).y == this.y;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + this.x;
            hash = 97 * hash + this.y;
            return hash;
        }
    }
}
