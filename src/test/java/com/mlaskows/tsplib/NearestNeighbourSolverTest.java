package com.mlaskows.tsplib;

import com.mlaskows.solvers.NearestNeighbourSolver;
import com.mlaskows.datamodel.Solution;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class NearestNeighbourSolverTest {


    @Test
    public void testSolution() {
        final NearestNeighbourSolver solver = new NearestNeighbourSolver
                (DataProvider.getAustralianDistances());
        final Solution solution = solver.getSolution();

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);

        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

}
