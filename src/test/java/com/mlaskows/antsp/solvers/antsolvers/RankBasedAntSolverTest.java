package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.BaseWithTspTest;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class RankBasedAntSolverTest implements BaseWithTspTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("tsplib/ali535.tsp");
        final RankedBasedConfig config =
                AcoConfigFactory.createDefaultRankedBasedConfig(tsp.getDimension());
        final StaticData data = new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withHeuristicSolution()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final RankBasedAntSolver solver = new RankBasedAntSolver(data, config);
        final Solution solution = solver.getSolution();

        List<Integer> nonImprovementPeriods = solution.getStatistics().get()
                .getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 256086);
    }

}
