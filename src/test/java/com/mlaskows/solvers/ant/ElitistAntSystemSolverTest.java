package com.mlaskows.solvers.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.SolverTest;
import com.mlaskows.tsplib.Item;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ElitistAntSystemSolverTest implements SolverTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Item item = getItem("ali535.tsp");
        final AcoConfig config =
                AcoConfigFactory.createDefaultAntSystemConfig(item.getDimension());
        final StaticMatricesHolder matrices = new StaticMatricesBuilder(item)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final AntSystemSolver solver = new ElitistAntSystemSolver(matrices, config);
        final Solution solution = solver.getSolution();

        List<Integer> nonImprovementPeriods = solver.getStatistics().getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 224358);
    }

}
