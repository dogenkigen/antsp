package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.tsplib.Item;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSystemSolverTest implements SolverTest {
    @Test
    public void testAustraliaSolution() throws IOException {
        final Item item = getItem("australia.tsp");
        StaticMatricesHolder matrices = new StaticMatricesBuilder(item)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(5)
                .build();
        final AcoConfig config = AcoConfigFactory.createDefaultAntSystemConfig(matrices
                .getDistanceMatrix().length);
        final MinMaxAntSystemSolver solver = new MinMaxAntSystemSolver(config, matrices);
        final Solution solution = solver.getSolution();

        //FIXME this fails randomly since algorithm is based on random values.
        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);
        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

    @Test
    public void testAli535Solution() throws IOException {
        final Item item = getItem("ali535.tsp");
        final AcoConfig config =
                AcoConfigFactory.createDefaultMinMaxConfig(item.getDimension());
        final StaticMatricesHolder matrices = new StaticMatricesBuilder(item)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final MinMaxAntSystemSolver solver = new MinMaxAntSystemSolver(config, matrices);
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
