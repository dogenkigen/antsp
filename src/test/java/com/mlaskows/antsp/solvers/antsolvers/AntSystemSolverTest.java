package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.AcoConfigBuilder;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.BaseWithTspTest;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.antsp.statistics.Statistics;
import com.mlaskows.tsplib.parser.TspLibParser;
import org.testng.annotations.Test;

import java.io.IOException;

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

    @Test
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

    @Test
    public void testPa561Solution() throws IOException {
        AcoConfig config = AcoConfigFactory.createAcoConfigBuilderWithDefaults(561)
                .withMaxStagnationCount(100)
                .withWithLocalSearch(true)
                .build();
        StaticMatrices matrices = new StaticMatricesBuilder(TspLibParser.parseTsp("tsplib/pa561.tsp"))
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();


        int len = 0;
        int its = 0;
        int iterations = 25;
        for (int i = 0; i < iterations; i++) {
            final Solution solution = new AntSystemSolver(matrices, config).getSolution();
            len += solution.getTourLength();
            its += solution.getStatistics().map(Statistics::getIterationsCount).orElse(0);
            System.out.println((((double) i / iterations) * 100) + "%");
        }
        System.out.println("avg len " + (len / iterations) + " after " + (its / iterations) + " iterations");
        System.out.println(TspLibParser.parseTour("tsplib/pa561.opt.tour").getComment());
        // No local search:
        // avg len 3270 after 195 iterations
        // Optimal tour for pa561 (2763)
        // with local search:
        // avg len 2925 after 174 iterations
        // Optimal tour for pa561 (2763)

        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        //assertTrue(solution.getTourLength() < 3422);
    }

}
