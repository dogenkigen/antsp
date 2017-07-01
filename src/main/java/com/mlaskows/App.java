package com.mlaskows;


import com.mlaskows.datamodel.Ant;
import com.mlaskows.tsplib.Item;
import com.mlaskows.tsplib.TspLibParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    // Representation of problem data
    //number of cities
    private static int numberOfCites;

    /**
     * distance matrix.
     * <p>
     * Where distance between two cities j and i is distanceMatrix[j][i]. Simple as that.
     */
    private static int distanceMatrix[][];

    /**
     * matrix with nearest neighbor lists of depth NN_FACTOR
     * In addition to the distance matrix, it is convenient to store for each
     * city a list of its nearest neighbors.
     * <p>
     * The position r of a city j in city i’s nearest-neighbor list
     * nearestNeighbors[i]  is the index of the distance dij in the sorted list di',
     * that is, nearestNeighbors[i][r]  gives the identifier (index) of the r-th
     * nearest city to city i (i.e., nearestNeighbors[i][r]  = j)
     */
    private static int nearestNeighbors[][];

    /**
     * An enormous speedup is obtained for the solution construction in ACO
     * algo- rithms, if the nearest-neighbor list is cut o¤ after a constant
     * number NN_FACTOR of nearest neighbors, where typically NN_FACTOR is a small value
     * ranging between 15 and 40.
     */
    private static final int NN_FACTOR = 40;


    /**
     * pheromoneMatrix matrix.
     * <p>
     * In addition to the instance-related information, we also have to store
     * for each connection (i, j) a number tij corresponding to the
     * pheromoneMatrix trail associated with that connection.
     */
    private static double pheromoneMatrix[][];

    /**
     * combined pheromoneMatrix and heuristic information for every pair i,j.
     * <p>
     * TODO Further optimization can be introduced by restricting the
     * computation
     * of the numbers in the choiceInfo matrix to the connections between
     * a city and the cities of its nearest-neighbor list.
     */
    private static double choiceInfo[][];

    /**
     * heuristic information matrix.
     * <p>
     * Can be used to compute choiceInfo value. Since heuristic information
     * never changes this can be done once and reused.
     * <p>
     * The heuristic information nij is typically inversely proportional to
     * the distance between cities i and j, a straightforward choice being
     * nij = 1/dij
     */
    private static double heuristicInformationMatrix[][];

    /**
     * Ants list.
     */
    private static List<Ant> ants;

    public static void main(String[] args) {
        //System.out.println(Runtime.getRuntime().availableProcessors());
        initializeData();

        boolean shouldTerminate = false;

        //Set parameters, initialize pheromoneMatrix trails
        while (shouldTerminate) {
            constructAntsSolutions();
            applyLocalSearch(); // optional
            updateStatistics();
            updatePheromones();
            shouldTerminate = true;
        }
    }

    private static void initializeData() {
        try {
            ClassLoader classLoader = App.class.getClassLoader();
            File file = new File(classLoader.getResource("ali535.tsp").getFile
                    ());
            Item item = TspLibParser.parse(file.getAbsolutePath());
            System.out.println(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
            ReadInstance ✓
            ComputeDistances ✓
            ComputeNearestNeighborLists ✓
            ComputeChoiceInformation
            InitializeAnts ✓
            InitializeParameters
            InitializeStatistics
         */
    }

    private static void updateStatistics() {
    }

    private static void constructAntsSolutions() {
    }

    private static void applyLocalSearch() {
    }

    private static void updatePheromones() {
    }

}
