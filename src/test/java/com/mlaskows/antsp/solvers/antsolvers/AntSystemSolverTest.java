package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.SolverTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.mlaskows.antsp.SolutionFactory.getAntSystemSolutionWithDefaultConfig;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolverTest implements SolverTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Solution solution =
                getAntSystemSolutionWithDefaultConfig(getFileAbsolutePath("ali535.tsp"));
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 224358);
    }

    @Test
    public void testAtt532Solution() throws IOException {
        final Solution solution =
                getAntSystemSolutionWithDefaultConfig(getFileAbsolutePath("att532.tsp"));

        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 33470);
    }

}
