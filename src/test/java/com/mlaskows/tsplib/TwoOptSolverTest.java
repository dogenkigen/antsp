package com.mlaskows.tsplib;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.matrices.StaticMatricesBuilder;
import com.mlaskows.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.TwoOptSolver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolverTest {
    @Test
    public void testSolution() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("australia.tsp")
                .getFile());
        final Item item = TspLibParser.parse(file.getAbsolutePath());

        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(item).withNearestNeighbors(5).build();
        final List<Integer> initialTour = List.of(0, 1, 2, 3, 4, 5);
        int initialDistnace = calculateDistance(matricesHolder.getDistanceMatrix(),
                initialTour);

        final TwoOptSolver solver = new TwoOptSolver
                (new Solution(initialTour, initialDistnace), matricesHolder);
        final Solution solution = solver.getSolution();

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);
        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

    private int calculateDistance(int[][] australianDistances, List<Integer> initialTour) {
        int distance = 0;
        for (int i = 0; i < initialTour.size() - 1; i++) {
            distance += australianDistances[initialTour.get(i)][initialTour.get(i + 1)];
        }
        return distance;
    }
}
