
import MapData.LevelData;
import MapData.Tile;
import Model.Cell.Guider;
import Model.Cell.Node;
import Model.GameElement.PathFinder;
import Model.GameElement.ShortestPathFinder;
import Model.GameElement.ShortestPathFinderA;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import org.junit.*;

/*
*Alrighty then, the epic research teststuffthingy
*It does two tests with identical maps with a "pillar" layout.
* The mighty dwarven halls of Moria XD
* ITS 5:25 AM!
* the maps are build as such:
* W for wall, E for Empty, S for start, T for target
* WEWWWWWEW
* ESEEEEEEE
* WEWEWEWEW
* EEEEEEEEW
* WEWEWEWEW
* EEEEEEEEW
* WEWEWEWEW
* EEEEEEETE
* WEWWWWWEW
*In the first test (testMapSize) it sees how fast it can compute 
*the optimal distance from top left to bottom right.
*It starts of with a map of 7 * 7, and continues on to maxsize.
*
*In the second test (testMapLoc) it takes a map of a set size, 
*then loops through every junction in the map and uses those junctions as start positions.
*the test logs the average calc time in the map resultDijkstra/resultAStar
*the test also logs the best distance in the map bestDijkstra/bestAstar
*for the location test the startlocation is also logged.

*it writes the results to CSV files in the pacman folder
*you should be able to import it in an excel.
*the column seperator is a comma, the row seperator is a newline if it asks.

*protip: dont put the testMapLoc on 61...
*I hope I can push this before I go to bed.
*after some calculations, I've figured that thats gonna be a no.
*900 nodes, 7E7 nanoSeconds per route, 100 routes per node, equals 6e12 nanoseconds or 6000 seconds -.-
*/
/**
 *
 * @author Regi
 */
public class Research {

    @Before
    public void setUp() {

    }

    @Test
    public void testMapSize() {
        int minTestSize = 5; //testSizes must be odd;
        int maxTestSize = 61;
        int smoothness = 100;
        HashMap<Integer, Double> resultsDijkstra = new HashMap();
        HashMap<Integer, Double> resultsAStar = new HashMap();
        HashMap<Integer, Double> bestDijkstra = new HashMap();
        HashMap<Integer, Double> bestAStar = new HashMap();

        for (int i = minTestSize; i < maxTestSize; i += 2) {
            Tile[][] baseArray = getMapSizeArray(i);
            LevelData data = new LevelData(baseArray);
            MockPlayGround playGround = new MockPlayGround(data);
            Guider startPos = playGround.getNodeAt(1, 1);
            Guider toPos = playGround.getNodeAt(playGround.getSize() - 2, playGround.getSize() - 2);
            TargetWrapper target = new TargetWrapper(toPos);
            ShortestPathFinder dijkstra = new ShortestPathFinder(target, playGround.getNodes(), playGround.getPaths());
            ShortestPathFinderA aStar = new ShortestPathFinderA(target, playGround.getPaths());

            double[] returned = test(smoothness, dijkstra, startPos);

            bestDijkstra.put(i, returned[0]);
            resultsDijkstra.put(i, returned[1]);

            returned = test(smoothness, aStar, startPos);

            bestAStar.put(i, returned[0]);
            resultsAStar.put(i, returned[1]);
            System.out.println("Did mapsize test #" + i);
        }
      
        String writeString = "Mapsize, results_dijkstra, result_aStar, best_dijkstra, best_aStar\n";
        for(int i = 0; i<maxTestSize;i++){
            if(resultsDijkstra.containsKey(i)){
                writeString += i + "," + resultsDijkstra.get(i) + "," + resultsAStar.get(i) + "," + bestDijkstra.get(i) + "," + bestAStar.get(i) +"\n";
            }
        }
        
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("mapSizeTest.csv"), "utf-8"));
            writer.write(writeString);
            
        } catch (IOException ex) {
            System.out.println("bla");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.out.println("bla");
            }
        }

    }

    @Test
    public void testMapLocation() {
        System.out.println("testMapLoc");
        int mapSize = 61; //must be an odd number
        int smoothness = 100;
        HashMap<Integer, int[]> testLocations = new HashMap();
        HashMap<Integer, Double> resultsDijkstra = new HashMap();
        HashMap<Integer, Double> resultsAStar = new HashMap();
        HashMap<Integer, Double> bestDijkstra = new HashMap();
        HashMap<Integer, Double> bestAStar = new HashMap();

        int i = 0;
        Tile[][] baseArray = getMapSizeArray(mapSize);
        LevelData data = new LevelData(baseArray);
        MockPlayGround playGround = new MockPlayGround(data);
        Guider toPos = playGround.getNodeAt(playGround.getSize() - 2, playGround.getSize() - 2);
        System.out.println(playGround.getSize()-2);
        for (Node node : playGround.getNodes()) {
            Guider startPos;
            if (node != toPos) {
                startPos = node;
            } else {
                continue;
            }
            testLocations.put(i, new int[]{node.getPositionX(), node.getPositionY()});
            TargetWrapper target = new TargetWrapper(toPos);
            ShortestPathFinder dijkstra = new ShortestPathFinder(target, playGround.getNodes(), playGround.getPaths());
            ShortestPathFinderA aStar = new ShortestPathFinderA(target, playGround.getPaths());
            double[] returned = test(smoothness, dijkstra, startPos);

            bestDijkstra.put(i, returned[0]);
            resultsDijkstra.put(i, returned[1]);

            returned = test(smoothness, aStar, startPos);

            bestAStar.put(i, returned[0]);
            resultsAStar.put(i, returned[1]);
            i++;
        }
        
        String writeString = "test#, test_loc_x, test_loc_y, results_dijkstra, result_aStar, best_dijkstra, best_aStar\n";
        for(int j = 0; j <= i;j++){
            if(resultsDijkstra.containsKey(j)){
                writeString += j + "," + testLocations.get(j)[0] + "," + testLocations.get(j)[1] +","+ resultsDijkstra.get(j) + "," + resultsAStar.get(j) + "," + bestDijkstra.get(j) + "," + bestAStar.get(j) +"\n";
            }
        }
        
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("mapLocationTest.csv"), "utf-8"));
            writer.write(writeString);
            
        } catch (IOException ex) {
            System.out.println("bla");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.out.println("bla");
            }
        }
    }

    private double[] test(int smoothness, PathFinder finder, Guider start) {
        double[] retDouble = new double[2];
        long startTime = System.nanoTime();

        for (int i = 0; i < smoothness; i++) {
            retDouble[0] = finder.getBestDistance(start);
        }
        long totalTime = System.nanoTime() - startTime;
        retDouble[1] = new Long(totalTime).doubleValue() / (double) smoothness;
        return retDouble;
    }

    //builds the "pillar" map
    private Tile[][] getMapSizeArray(int size) {
        Tile[][] testArray = new Tile[size + 2][size + 2];
        for (int i = 1; i < testArray.length - 1; i++) {
            Tile[] tempArr = new Tile[testArray[i].length];
            if (i % 2 == 1) {
                for (int j = 1; j < testArray[i].length; j++) {
                    tempArr[j] = Tile.EMPTY;
                }
            } else {
                for (int j = 1; j < testArray[i].length; j++) {
                    if (j % 2 == 1) {
                        tempArr[j] = Tile.EMPTY;
                    } else {
                        tempArr[j] = Tile.WALL;
                    }
                }

            }
            testArray[i] = tempArr;
        }

        for (int i = 0; i < testArray.length; i++) {
            if (i != 1 && i != testArray.length - 2) {
                testArray[i][0] = Tile.WALL;
                testArray[i][testArray[i].length - 1] = Tile.WALL;
            } else {
                testArray[i][0] = Tile.EMPTY;
                testArray[i][testArray[i].length - 1] = Tile.EMPTY;
            }
        }

        for (int i = 0; i < testArray[0].length; i++) {
            if (i != 1 && i != testArray[0].length - 2) {
                testArray[0][i] = Tile.WALL;
                testArray[testArray.length - 1][i] = Tile.WALL;
            } else {
                testArray[0][i] = Tile.EMPTY;
                testArray[testArray.length - 1][i] = Tile.EMPTY;
            }
        }
        return testArray;
    }
}
