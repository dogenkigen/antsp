package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.BaseWithTspTest;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import com.mlaskows.antsp.config.AcoConfigFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ElitistAntSolverTest implements BaseWithTspTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("tsplib/ali535.tsp");
        final AcoConfig config =
                AcoConfigFactory.createDefaultElitistConfig(tsp.getDimension());
        final StaticData data = new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withHeuristicSolution()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final ElitistAntSolver solver = new ElitistAntSolver(data, config);
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
