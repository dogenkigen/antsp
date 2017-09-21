package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.MinMaxConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.BaseWithTspTest;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.statistics.Statistics;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSolverTest implements BaseWithTspTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("tsplib/ali535.tsp");
        final MinMaxConfig config = AcoConfigFactory
                .createDefaultMinMaxConfig(tsp.getDimension());
        final StaticMatrices matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final MinMaxAntSolver solver = new MinMaxAntSolver(matrices,
                config);
        final Solution solution = solver.getSolution();

        List<Integer> nonImprovementPeriods = solution.getStatistics().get()
                .getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 224358,
                "Actual solution length is" + solution.getTourLength());
    }

    @Test
    public void testAtt532Solution() throws IOException {
        final long l = currentTimeMillis();
        final Tsp tsp = getTsp("tsplib/att532.tsp");
        final MinMaxConfig config =
                AcoConfigFactory.createDefaultMinMaxConfig(tsp.getDimension());
        final StaticMatrices matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final MinMaxAntSolver solver = new MinMaxAntSolver(matrices, config);
        final Solution solution = solver.getSolution();
        final long l1 = currentTimeMillis() - l;

        final Statistics statistics = solution.getStatistics().get();
        List<Integer> nonImprovementPeriods = statistics.getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        System.out.println("Solution: " + solution.getTourLength() + " after:" +
                " " +
                 + statistics.getIterationsCount() + " iterations in " + l1+
                "ms");
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 33470);
    }
}
