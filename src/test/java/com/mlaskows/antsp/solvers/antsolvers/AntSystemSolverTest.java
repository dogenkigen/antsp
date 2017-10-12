package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.BaseWithTspTest;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.antsp.statistics.Statistics;
import com.mlaskows.tsplib.parser.TspLibParser;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.mlaskows.antsp.SolutionFactory.getAntSystemSolution;
import static com.mlaskows.antsp.SolutionFactory.getAntSystemSolutionWithDefaultConfig;
import static org.testng.Assert.assertTrue;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolverTest implements BaseWithTspTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Solution solution =
                getAntSystemSolutionWithDefaultConfig(getFileAbsolutePath("tsplib/ali535.tsp"));
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        assertTrue(solution.getTourLength() < 224358);
    }

    @Test(enabled = false)
    public void testAtt532Solution() throws IOException {
        final Solution solution =
                getAntSystemSolutionWithDefaultConfig(getFileAbsolutePath("tsplib/att532.tsp"));

        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        assertTrue(solution.getTourLength() < 33470);
    }

    @Test
    public void testBerlin52Solution() throws IOException {
        // More ants then cities
        final AcoConfig config = AcoConfigFactory
                .createDefaultAntSystemConfig(100);
        final Solution solution = getAntSystemSolution(getFileAbsolutePath("tsplib/berlin52.tsp"), config);

        assertTrue(solution.getTourLength() < 8314);
    }

}
