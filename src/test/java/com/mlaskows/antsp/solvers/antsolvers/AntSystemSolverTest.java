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
    public void testStats() throws IOException {
        boolean localSearch = true;
        int iterations = 10;

        //int numberOfAnts = 100;
        int nnFactor = 15;
        String name = "pa561";
        StaticMatrices matrices = new StaticMatricesBuilder(TspLibParser.parseTsp("tsplib/" + name + ".tsp"))//pa561
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(nnFactor)
                .build();


        StringBuilder allDataStringBuilder = new StringBuilder();
        StringBuilder chartStringBuilder = new StringBuilder();
        allDataStringBuilder.append("NN_FACTOR,ANT_COUNT,LOCAL_SEARCH,TOUR_LEN,SOLUTION_FOUND_IN_IT,NON_IMPROVED_PERIODS\n");
        int maxNumberOfAnts = matrices.getProblemSize();
        for (int numberOfAnts = 1; numberOfAnts <= maxNumberOfAnts; numberOfAnts += 10) {
            AcoConfig config = AcoConfigFactory.createAcoConfigBuilderWithDefaults(numberOfAnts)
                    .withMaxStagnationCount(100)
                    .withWithLocalSearch(localSearch)
                    .withNearestNeighbourFactor(nnFactor)
                    .build();
            for (int i = 0; i < iterations; i++) {
                final Solution solution = new AntSystemSolver(matrices, config).getSolution();
                Statistics statistics = solution.getStatistics().get();

                allDataStringBuilder.append(nnFactor);
                allDataStringBuilder.append(",");
                allDataStringBuilder.append(numberOfAnts);
                allDataStringBuilder.append(",");
                allDataStringBuilder.append(localSearch);
                allDataStringBuilder.append(",");
                allDataStringBuilder.append(solution.getTourLength());
                allDataStringBuilder.append(",");
                allDataStringBuilder.append(statistics.getIterationsCount() - config.getMaxStagnationCount());
                allDataStringBuilder.append(",");
                statistics.getNonImprovementPeriods().stream().forEach(p -> allDataStringBuilder.append(p + ";"));
                allDataStringBuilder.append("\n");

                chartStringBuilder.append(numberOfAnts);
                chartStringBuilder.append(",");
                chartStringBuilder.append(solution.getTourLength());
                chartStringBuilder.append("\n");

                //System.out.println(allDataStringBuilder);
                System.out.println(((double) (((numberOfAnts - 1) * iterations) + i) / ((maxNumberOfAnts) * iterations)) * 100 + "%");

            }
        }
        String ls = localSearch ? "_LS_" : "";
        Path path = Paths.get(name + ls + ".csv");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(allDataStringBuilder.toString());
        }

        Path chartPath = Paths.get(name + ls + "_chart.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(chartPath)) {
            writer.write(chartStringBuilder.toString());
        }


        //int len = 0;
        //int its = 0;

        //System.out.println("avg len " + (len / iterations) + " after " + (its / iterations) + " iterations");
        //System.out.println(TspLibParser.parseTour("tsplib/pa561.opt.tour").getComment());
        // 561 ants:
        // No local search:
        // avg len 3270 after 195 iterations
        // Optimal tour for pa561 (2763)
        // with local search:
        // avg len 2925 after 174 iterations
        // Optimal tour for pa561 (2763)

        //100 ants:
        // No LS
        // avg len 3361 after 166 iterations
        // Optimal tour for pa561 (2763)
        // With LS
        // avg len 2960 after 171 iterations
        // Optimal tour for pa561 (2763)

        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        //assertTrue(solution.getTourLength() < 3422);
    }

}
