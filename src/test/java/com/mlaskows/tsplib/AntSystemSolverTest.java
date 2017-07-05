package com.mlaskows.tsplib;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.matrices.StaticMatricesBuilder;
import com.mlaskows.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.AntSystemSolver;
import com.mlaskows.datamodel.Solution;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolverTest implements SolverTest {

    private static final int NN_FACOTR = 5;
    private static StaticMatricesHolder matrices;

    @BeforeClass
    public void init() throws IOException {
        final Item item = getItem("australia.tsp");
        matrices = new StaticMatricesBuilder(item).withHeuristicInformationMatrix().withNearestNeighbors(NN_FACOTR).build();
    }

    @Test
    public void testSolution() {
        final AcoConfig config = AcoConfigFactory.createDefaultAntSystemConfig(matrices
                .getDistanceMatrix().length);
        final AntSystemSolver solver = new AntSystemSolver(config, matrices);
        final Solution solution = solver.getSolution();

        //FIXME make it work
        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);
        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

}
