package com.mlaskows.solvers;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.heuristic.TwoOptSolver;
import com.mlaskows.tsplib.datamodel.Tsp;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolverTest implements SolverTest {
    @Test
    public void testAustraliaSolution() throws IOException {
        Tsp tsp = getTsp("australia.tsp");

        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(5)
                .build();
        final List<Integer> initialTour = List.of(0, 1, 2, 3, 4, 5);
        int initialDistnace = calculateDistance(matricesHolder.getDistanceMatrix(),
                initialTour);

        final Solution solution = computeSolution(initialTour, matricesHolder, initialDistnace);

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);
        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("ali535.tsp");

        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        final List<Integer> initialTour = getInitialTour(535);
        int initialDistnace = calculateDistance(matricesHolder.getDistanceMatrix(),
                initialTour);

        final Solution solution = computeSolution(initialTour, matricesHolder, initialDistnace);

        Assert.assertTrue(solution.getTourLength() < initialDistnace);
        Assert.assertEquals(solution.getTourLength(), 3669906);
    }

    @Test
    public void testBerlin52() throws IOException {
        Tsp tsp = getTsp("berlin52.tsp");
        List<Integer> initialTour = getInitialTour(52);
        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistnace = calculateDistance(matricesHolder.getDistanceMatrix(),
                initialTour);

        final Solution solution = computeSolution(initialTour, matricesHolder, initialDistnace);

        Assert.assertTrue(solution.getTourLength() < initialDistnace);
    }

    @Test(enabled = false)
    public void testUsa13509() throws IOException {
        Tsp tsp = getTsp("usa13509.tsp");
        List<Integer> initialTour = getInitialTour(13509);

        StaticMatricesHolder matricesHolderWithNN = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistnace = calculateDistance(matricesHolderWithNN.getDistanceMatrix(),
                initialTour);

        long nnStartTime = System.currentTimeMillis();
        final Solution solutionWithNN = computeSolution(initialTour, matricesHolderWithNN, initialDistnace);
        long nnTime = System.currentTimeMillis() - nnStartTime;

        System.out.println("NN test done in " + nnTime + " ms and solution " +
                "len " + solutionWithNN.getTourLength());

        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(tsp).build();

        long startTime = System.currentTimeMillis();
        Solution solution = computeSolution(initialTour, matricesHolder, initialDistnace);
        long time = System.currentTimeMillis() - startTime;

        System.out.println("no NN test done in " + time + " ms and solution" +
                "len " + solution.getTourLength());

        Assert.assertTrue(solution.getTourLength() < solutionWithNN.getTourLength());
    }

    private Solution computeSolution(List<Integer> initialTour, StaticMatricesHolder matricesHolder, int initialDistnace) {
        final TwoOptSolver solver = new TwoOptSolver
                (new Solution(initialTour, initialDistnace), matricesHolder);
        return solver.getSolution();
    }

    private List<Integer> getInitialTour(int size) {
        final List<Integer> initialTour = new ArrayList<>(size);
        IntStream.range(0, size).forEach(index -> initialTour.add(index));
        return initialTour;
    }

    private int calculateDistance(int[][] australianDistances, List<Integer> initialTour) {
        int distance = 0;
        for (int i = 0; i < initialTour.size() - 1; i++) {
            distance += australianDistances[initialTour.get(i)][initialTour.get(i + 1)];
        }
        return distance;
    }
}
