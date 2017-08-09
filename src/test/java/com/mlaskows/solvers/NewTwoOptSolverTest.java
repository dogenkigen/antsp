package com.mlaskows.solvers;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.solvers.heuristic.NewTwoOptSolver;
import com.mlaskows.tsplib.datamodel.Tsp;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class NewTwoOptSolverTest extends TwoOptSolverTest {

    @Test
    public void testBerlin52() throws IOException {
        Tsp tsp = getTsp("berlin52.tsp");
        List<Integer> initialTour = getInitialTour(52);
        StaticMatrices matricesHolder = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistnace = calculateDistance(matricesHolder.getDistanceMatrix(),
                initialTour);

        final NewTwoOptSolver solver = new NewTwoOptSolver
                (new Solution(initialTour, initialDistnace), matricesHolder);
        final Solution solution = solver.getSolution();

        assertTrue(solution.getTourLength() < initialDistnace);
        assertTrue(solution.getTourLength() < 11000);
    }

}
