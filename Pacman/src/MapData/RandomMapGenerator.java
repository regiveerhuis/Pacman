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

    private int minimumGhostDistance = 5;
    private int superDotAmount = 4;
    private double connectivity = 0.3;
    private int nodeAmount = 8;

    public LevelData getMap(int xSize, int ySize, int seed) {
        ArrayList<Vector> nodes = new ArrayList();
        Random rand = new Random(seed);
        Tile[][] tiles = new Tile[xSize - 1][ySize - 1];
        int pacmanX = rand.nextInt(tiles.length);
        int pacmanY = rand.nextInt(tiles[0].length);
        tiles[pacmanX][pacmanY] = Tile.PACMAN;
        nodes.add(new Vector(pacmanX, pacmanY));

        Vector[] loc = new Vector[4];

        loc[0] = new Vector(rand.nextInt(tiles.length), tiles[0].length - 1);
        loc[1] = new Vector(rand.nextInt(tiles.length), 0);
        loc[2] = new Vector(tiles.length - 1, rand.nextInt(tiles[0].length));
        loc[3] = new Vector(0, rand.nextInt(tiles[0].length));
        for (Vector location : loc) {
            if (!nodes.contains(location)) {
                nodes.add(location);
                tiles[location.x][location.y] = Tile.EMPTY;
            }
        }

        for (int i = 0; i < nodeAmount - 4; i++) {
            boolean notMadeNode = true;
            while (notMadeNode) {
                System.out.println("notMadeNode");
                Vector location = new Vector(rand.nextInt(tiles.length), rand.nextInt(tiles[0].length));
                if (!nodes.contains(location)) {
                    nodes.add(location);
                    tiles[location.x][location.y] = Tile.EMPTY;
                    notMadeNode = false;
                }
            }
        }

        createPaths(tiles, nodes, rand);
        tiles = fillWalls(tiles);
        for (int i = 0; i < 4; i++) {          
            ArrayList<Vector> possLocs = new ArrayList<>();
            xLoop:
            for (int x = 0; x < tiles.length; x++) {
                for(int y =0; y<tiles[0].length; y++){
                    if(y >= pacmanY + minimumGhostDistance || y <= pacmanY - minimumGhostDistance || x >= pacmanX + minimumGhostDistance || x <= pacmanY - minimumGhostDistance){
                        if(tiles[x][y] == Tile.WALL){
                            if(x > 0 && tiles[x-1][y] != Tile.WALL){
                                possLocs.add(new Vector(x,y));
                            }
                            if(x < tiles.length - 1 && tiles[x+1][y] != Tile.WALL){
                                possLocs.add(new Vector(x,y));
                            }
                            if(y > 0 && tiles[x][y-1] != Tile.WALL){
                                possLocs.add(new Vector(x,y));
                            }
                            if(y < tiles[0].length - 1 && tiles[x][y+1] != Tile.WALL){
                                possLocs.add(new Vector(x,y));
                            }
                        }
                    }
                } 
            }
            if(possLocs.size() < 1){
                throw new IllegalStateException("No place for ghosts!");
            }
            Vector ghostLoc = possLocs.get(rand.nextInt(possLocs.size()));
            tiles[ghostLoc.x][ghostLoc.y] = Tile.GHOST;
        }
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
            System.out.println("unusedNodes");
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

            if (curConnections.get(targetVector).size() == wantedConnections.get(targetVector)) {
                filled.add(targetVector);
            } else {
                partialUsed.add(targetVector);
            }

            if (curConnections.get(startVector).size() == wantedConnections.get(startVector)) {
                filled.add(startVector);
            }
        }

        while (partialUsed.size() > 0) {
            System.out.println("partialused");
            Vector startVector = getRandomVector(partialUsed, rand);
            Vector targetVector = getClosestVector(partialUsed, startVector, rand);
            if (targetVector == null) {
                targetVector = startVector;
            }
            if (!(startVector == targetVector && curConnections.get(startVector).size() >= 3)) {

                curConnections.get(startVector).add(targetVector);
                curConnections.get(targetVector).add(startVector);

                if (curConnections.get(startVector).size() >= wantedConnections.get(startVector)) {
                    filled.add(startVector);
                    partialUsed.remove(startVector);
                }

                if (curConnections.get(targetVector).size() >= wantedConnections.get(targetVector)) {
                    filled.add(targetVector);
                    partialUsed.remove(targetVector);
                }
            }

            if (partialUsed.size() == 1) {
                for (Vector vector : partialUsed) {
                    if (curConnections.get(vector).size() >= 3) {
                        filled.add(vector);
                        partialUsed.remove(vector);
                    }
                }
            }
        }

        for (Vector vector : curConnections.keySet()) {
            for (int i = 0; i < curConnections.get(vector).size(); i++) {
                Vector v = curConnections.get(vector).get(i);
                createPath(tiles, vector, v, rand);
                curConnections.get(vector).remove(v);
                curConnections.get(v).remove(vector);
            }
        }
    }

    private Tile[][] fillWalls(Tile[][] tiles) {

        Tile[][] encappedTiles = new Tile[tiles.length + 2][tiles[0].length + 2];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                encappedTiles[x + 1][y + 1] = tiles[x][y];
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

    private void createPath(Tile[][] tiles, Vector vector, Vector v, Random rand) {

        Vector cur = vector;
        Vector to = cur.getVectorTo(v);
        while (to.x != 0 || to.y != 0) {
            ArrayList<Direction> possDir = new ArrayList();
            if (to.x > 0) {
                possDir.add(Direction.EAST);
            } else if (to.x < 0) {
                possDir.add(Direction.WEST);
            }

            if (to.y > 0) {
                possDir.add(Direction.SOUTH);
            } else if (to.y < 0) {
                possDir.add(Direction.NORTH);
            }

            Direction dir = possDir.get(rand.nextInt(possDir.size()));
            switch (dir) {
                case EAST:
                    cur = cur.addVector(new Vector(1, 0));
                    break;
                case WEST:
                    cur = cur.addVector(new Vector(-1, 0));
                    break;
                case NORTH:
                    cur = cur.addVector(new Vector(0, -1));
                    break;
                case SOUTH:
                    cur = cur.addVector(new Vector(0, 1));
                    break;
            }
            System.out.println(dir);
            System.out.println(cur);
            Tile tile = tiles[cur.x][cur.y];
            if (tile != Tile.GHOST && tile != Tile.PACMAN) {
                tiles[cur.x][cur.y] = Tile.EMPTY;
            }
            to = cur.getVectorTo(v);
        }
    }

    private Vector getClosestVector(Set<Vector> partialUsed, Vector vector, Random rand) {
        Vector smallest = null;
        Vector toSmallest = null;

        for (Vector v : partialUsed) {
            if (v != vector) {
                Vector to = vector.getVectorTo(v);

                if (smallest == null || Math.abs(to.x) + Math.abs(to.y) < Math.abs(toSmallest.x) + Math.abs(toSmallest.y)) {
                    smallest = v;
                    toSmallest = to;

                }
            }
        }
        System.out.println(smallest);
        return smallest;
    }

    private class Vector {

        int x;
        int y;

        Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Vector addVector(Vector v1) {
            return new Vector(this.x + v1.x, this.y + v1.y);
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
