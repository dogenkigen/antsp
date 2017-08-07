package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.config.MinMaxConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.SolverTest;
import com.mlaskows.tsplib.datamodel.Tsp;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSolverTest implements SolverTest {
    @Test
    public void testAustraliaSolution() throws IOException {
        final Tsp tsp = getTsp("australia.tsp");
        StaticMatricesHolder matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(5)
                .build();
        final MinMaxConfig config = AcoConfigFactory
                .createDefaultMinMaxConfig(matrices.getProblemSize());
        final MinMaxAntSolver solver = new MinMaxAntSolver(matrices,
                config);
        final Solution solution = solver.getSolution();

        //FIXME this fails randomly since algorithm is based on random values.
        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);
        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("ali535.tsp");
        final MinMaxConfig config = AcoConfigFactory
                .createDefaultMinMaxConfig(tsp.getDimension());
        final StaticMatricesHolder matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final MinMaxAntSolver solver = new MinMaxAntSolver(matrices,
                config);
        final Solution solution = solver.getSolution();

        List<Integer> nonImprovementPeriods = solver.getStatistics().getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 224358,
                "Actual solution length is" + solution.getTourLength());
    }
}
