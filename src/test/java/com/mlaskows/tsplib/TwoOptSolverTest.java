package com.mlaskows.tsplib;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.solvers.TwoOptSolver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolverTest {
    @Test
    public void testSolution() {

        final int[][] australianDistances = DataProvider.getAustralianDistances();
        final List<Integer> initialTour = List.of(0, 1, 2, 3, 4, 5);
        int initialDistnace = calculateDistance(australianDistances,
                initialTour);
        final TwoOptSolver solver = new TwoOptSolver
                (new Solution(initialTour, initialDistnace), australianDistances);
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
