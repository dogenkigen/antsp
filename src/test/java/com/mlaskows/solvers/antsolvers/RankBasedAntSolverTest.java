package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.config.RankedBasedConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.SolverTest;
import com.mlaskows.tsplib.datamodel.Tsp;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class RankBasedAntSolverTest implements SolverTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("ali535.tsp");
        final RankedBasedConfig config =
                AcoConfigFactory.createDefaultRankedBasedConfig(tsp.getDimension());
        final StaticMatrices matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final RankBasedAntSolver solver = new RankBasedAntSolver(matrices, config);
        final Solution solution = solver.getSolution();

        List<Integer> nonImprovementPeriods = solver.getStatistics().getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 224358);
    }

}
