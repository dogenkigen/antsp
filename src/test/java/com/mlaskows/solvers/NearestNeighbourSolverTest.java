package com.mlaskows.solvers;

import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.solvers.heuristic.NearestNeighbourSolver;
import com.mlaskows.tsplib.datamodel.Tsp;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class NearestNeighbourSolverTest implements SolverTest {


    @Test
    public void testAustraliaSolution() throws IOException {
        final Solution solution = getSolution("australia.tsp");

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);

        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

    @Test
    public void testAli535Solution() throws IOException {
        final Solution solution = getSolution("ali535.tsp");

        Assert.assertEquals(solution.getTourLength(), 224358);
    }

    private Solution getSolution(String fileName) throws IOException {
        final Tsp tsp = getTsp(fileName);
        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(tsp).build();

        final NearestNeighbourSolver solver = new NearestNeighbourSolver
                (matricesHolder);
        return solver.getSolution();
    }

}
