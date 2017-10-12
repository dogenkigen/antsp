package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.antsp.statistics.Statistics;
import com.mlaskows.tsplib.parser.TspLibParser;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;

public class StatsSolverTest {

    private static final int ITERATIONS = 10;
    private static final int MAX_STAGNATION_COUNT = 100;
    private static final int DEFAULT_NN_FACTOR = 15;

    private BiFunction<StaticMatrices, AcoConfig, Solution> antSystem =
            (matrices, config) -> new AntSystemSolver(matrices, config).getSolution();

    private BiFunction<StaticMatrices, AcoConfig, Solution> maxMin =
            (matrices, config) -> new MaxMinAntSolver(matrices, (MaxMinConfig) config).getSolution();

    private BiFunction<Integer, Boolean, AcoConfig> acoConfig =
            (numberOfAnts, localSearch) -> AcoConfigFactory.createAcoConfigBuilderWithDefaults(numberOfAnts)
                    .withMaxStagnationCount(MAX_STAGNATION_COUNT)
                    .withWithLocalSearch(localSearch)
                    .withNearestNeighbourFactor(DEFAULT_NN_FACTOR)
                    .build();

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                {"berlin52", false, 1, antSystem, acoConfig},
                {"berlin52", true, 1, antSystem, acoConfig},
                /*{"gr202", false, 5, antSystem, acoConfig},
                {"gr202", true, 5, antSystem, acoConfig},
                {"pa561", false, 10, antSystem, acoConfig},
                {"pa561", true, 10, antSystem, acoConfig},
                {"dsj1000", false, 20, antSystem, acoConfig},
                {"dsj1000", true, 20, antSystem, acoConfig},*/
        };
    }


    @Test(dataProvider = "getData")
    public void testStatsIncrementAnts(String name,
                                       boolean localSearch,
                                       int step,
                                       BiFunction<StaticMatrices, AcoConfig, Solution> solving,
                                       BiFunction<Integer, Boolean, AcoConfig> configurating) throws IOException {
        StaticMatrices matrices = getMatrices(DEFAULT_NN_FACTOR, name);
        StringBuilder stringBuilder = initStringBuilder();
        int maxNumberOfAnts = matrices.getProblemSize();
        for (int numberOfAnts = 1; numberOfAnts <= maxNumberOfAnts; numberOfAnts += step) {
            AcoConfig config = configurating.apply(numberOfAnts, localSearch);
            for (int i = 0; i < ITERATIONS; i++) {
                final Solution solution = solving.apply(matrices, config);
                appendStringBuilder(localSearch, DEFAULT_NN_FACTOR, stringBuilder, numberOfAnts, config, solution);
                System.out.println(((double) (((numberOfAnts - 1) * ITERATIONS) + i) / ((maxNumberOfAnts) * ITERATIONS)) * 100 + "%");
            }
        }
        writeToFile(localSearch, name, stringBuilder);
    }

    private void appendStringBuilder(boolean localSearch, int nnFactor, StringBuilder stringBuilder, int numberOfAnts, AcoConfig config, Solution solution) {
        Statistics statistics = solution.getStatistics().get();
        stringBuilder.append(nnFactor);
        stringBuilder.append(",");
        stringBuilder.append(numberOfAnts);
        stringBuilder.append(",");
        stringBuilder.append(localSearch);
        stringBuilder.append(",");
        stringBuilder.append(solution.getTourLength());
        stringBuilder.append(",");
        stringBuilder.append(statistics.getIterationsCount() - config.getMaxStagnationCount());
        stringBuilder.append(",");
        statistics.getNonImprovementPeriods().stream().forEach(p -> stringBuilder.append(p + ";"));
        stringBuilder.append("\n");
    }

    private void writeToFile(boolean localSearch, String name, StringBuilder stringBuilder) throws IOException {
        String ls = localSearch ? "_LS_" : "";
        Path path = Paths.get(name + ls + ".csv");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(stringBuilder.toString());
        }
    }

    private StringBuilder initStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NN_FACTOR,ANT_COUNT,LOCAL_SEARCH,TOUR_LEN,SOLUTION_FOUND_IN_IT,NON_IMPROVED_PERIODS\n");
        return stringBuilder;
    }

    private StaticMatrices getMatrices(int nnFactor, String name) throws IOException {
        return new StaticMatricesBuilder(TspLibParser.parseTsp("tsplib/" + name + ".tsp"))
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(nnFactor)
                .build();
    }

}
