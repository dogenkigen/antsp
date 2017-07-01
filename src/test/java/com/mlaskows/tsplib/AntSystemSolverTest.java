package com.mlaskows.tsplib;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.matrices.MatricesFactory;
import com.mlaskows.matrices.MatricesHolder;
import com.mlaskows.solution.AntSystemSolver;
import com.mlaskows.solution.Solution;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolverTest {

    private static final int NN_FACOTR = 5;
    private static MatricesHolder matrices;

    @BeforeClass
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("australia.tsp")
                .getFile());
        final Item item = TspLibParser.parse(file.getAbsolutePath());
        final MatricesFactory matricesFactory = new MatricesFactory(item, NN_FACOTR);
        matrices = matricesFactory.createMatrices();
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
