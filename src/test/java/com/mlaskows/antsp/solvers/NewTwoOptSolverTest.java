package com.mlaskows.antsp.solvers;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.antsp.solvers.heuristic.NewTwoOptSolver;
import com.mlaskows.tsplib.datamodel.Tsp;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class NewTwoOptSolverTest extends TwoOptSolverTest {

    @Test
    public void testBerlin52() throws IOException {
        Tsp tsp = getTsp("tsplib/berlin52.tsp");
        List<Integer> initialTour = getInitialTour(52);
        StaticMatrices matricesHolder = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistance = calculateDistance(matricesHolder.getDistanceMatrix(),
                initialTour);

        final NewTwoOptSolver solver = new NewTwoOptSolver
                (new Solution(initialTour, initialDistance), matricesHolder);
        final Solution solution = solver.getSolution();

        assertTrue(solution.getTourLength() < initialDistance);
        assertTrue(solution.getTourLength() < 11000);
    }

}
