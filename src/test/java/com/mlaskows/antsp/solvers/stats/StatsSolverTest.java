package com.mlaskows.antsp.solvers.stats;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.antsp.solvers.antsolvers.ElitistAntSolver;
import com.mlaskows.antsp.solvers.antsolvers.MaxMinAntSolver;
import com.mlaskows.antsp.solvers.antsolvers.RankBasedAntSolver;
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
    private static final int MIN_NN_FACTOR = 15;
    private static final int MAX_NN_FACTOR = 40;

    private BiFunction<StaticData, AcoConfig, Solution> antSystem =
            (data, config) -> new AntSystemSolver(data, config).getSolution();

    private BiFunction<StaticData, AcoConfig, Solution> elitist =
            (data, config) -> new ElitistAntSolver(data, config).getSolution();

    private BiFunction<StaticData, AcoConfig, Solution> rankBased =
            (data, config) -> new RankBasedAntSolver(data, (RankedBasedConfig) config).getSolution();

    private BiFunction<StaticData, AcoConfig, Solution> maxMin =
            (data, config) -> new MaxMinAntSolver(data, (MaxMinConfig) config).getSolution();

    private BiFunction<Integer, Boolean, AcoConfig> acoConfigIncrementAnts =
            (numberOfAnts, localSearch) -> AcoConfigFactory.createAcoConfigBuilderWithDefaults(numberOfAnts)
                    .withMaxStagnationCount(MAX_STAGNATION_COUNT)
                    .withWithLocalSearch(localSearch)
                    .withNearestNeighbourFactor(MIN_NN_FACTOR)
                    .build();

    private BiFunction<Integer, Boolean, AcoConfig> elitistConfigIncrementAnts =
            (numberOfAnts, localSearch) -> AcoConfigFactory.createElitistConfigBuilderWithDefaults(numberOfAnts)
                    .withMaxStagnationCount(MAX_STAGNATION_COUNT)
                    .withWithLocalSearch(localSearch)
                    .withNearestNeighbourFactor(MIN_NN_FACTOR)
                    .build();

    private BiFunction<Integer, Boolean, AcoConfig> rankBasedConfigIncrementAnts =
            (numberOfAnts, localSearch) -> AcoConfigFactory.createRankBasedConfigBuilderWithDefaults(numberOfAnts)
                    .withMaxStagnationCount(MAX_STAGNATION_COUNT)
                    .withWithLocalSearch(localSearch)
                    .withNearestNeighbourFactor(MIN_NN_FACTOR)
                    .build();

    private BiFunction<Integer, Boolean, AcoConfig> maxMinConfigIncrementAnts =
            (numberOfAnts, localSearch) -> AcoConfigFactory.createMaxMinConfigBuilderWithDefaults(numberOfAnts)
                    .withReinitializationCount(80)
                    .withMaxStagnationCount(MAX_STAGNATION_COUNT)
                    .withWithLocalSearch(localSearch)
                    .withNearestNeighbourFactor(MIN_NN_FACTOR)
                    .withPheromoneEvaporationFactor(0.05)
                    .build();

    private TriFunction<Integer, Integer, Boolean, AcoConfig> acoConfigIncrementNN =
            (nnFactor, numberOfAnts, localSearch) -> AcoConfigFactory.createAcoConfigBuilderWithDefaults(numberOfAnts)
                    .withMaxStagnationCount(MAX_STAGNATION_COUNT)
                    .withNearestNeighbourFactor(nnFactor)
                    .withWithLocalSearch(localSearch)
                    .build();


    @DataProvider
    public Object[][] incrementAntsProvider() {
        return new Object[][]{
                /*{"berlin52", false, 1, antSystem, acoConfigIncrementAnts, "as"},
                {"berlin52", true, 1, antSystem, acoConfigIncrementAnts, "as"},
                {"gr202", false, 5, antSystem, acoConfigIncrementAnts, "as"},
                {"gr202", true, 5, antSystem, acoConfigIncrementAnts, "as"},
                {"pa561", false, 10, antSystem, acoConfigIncrementAnts, "as"},
                {"pa561", true, 10, antSystem, acoConfigIncrementAnts, "as"},
                {"dsj1000", false, 20, antSystem, acoConfigIncrementAnts, "as"},
                {"dsj1000", true, 20, antSystem, acoConfigIncrementAnts, "as"},*/

                /*{"berlin52", false, 1, elitist, elitistConfigIncrementAnts, "elitist"},
                {"berlin52", true, 1, elitist, elitistConfigIncrementAnts, "elitist"},
                {"gr202", false, 5, elitist, elitistConfigIncrementAnts, "elitist"},
                {"gr202", true, 5, elitist, elitistConfigIncrementAnts, "elitist"},
                {"pa561", false, 10, elitist, elitistConfigIncrementAnts, "elitist"},
                {"pa561", true, 10, elitist, elitistConfigIncrementAnts, "elitist"},
                {"dsj1000", false, 20, elitist, elitistConfigIncrementAnts, "elitist"},
                {"dsj1000", true, 20, elitist, elitistConfigIncrementAnts, "elitist"},*/

                /*{"berlin52", false, 1, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"berlin52", true, 1, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"gr202", false, 5, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"gr202", true, 5, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"pa561", false, 10, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"pa561", true, 10, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"dsj1000", false, 20, rankBased, rankBasedConfigIncrementAnts, "rankBased"},
                {"dsj1000", true, 20, rankBased, rankBasedConfigIncrementAnts, "rankBased"},*/

                {"berlin52", false, 1, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                {"berlin52", true, 1, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                {"gr202", false, 5, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                {"gr202", true, 5, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                {"pa561", false, 10, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                {"pa561", true, 10, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                /*{"dsj1000", false, 20, maxMin, maxMinConfigIncrementAnts, "maxMin"},
                {"dsj1000", true, 20, maxMin, maxMinConfigIncrementAnts, "maxMin"},*/
        };
    }

    @Test(dataProvider = "incrementAntsProvider")
    public void testStatsIncrementAnts(String name,
                                       boolean localSearch,
                                       int step,
                                       BiFunction<StaticData, AcoConfig, Solution> solving,
                                       BiFunction<Integer, Boolean, AcoConfig> configuring,
                                       String algorithmName) throws IOException {
        StaticData data = getMatrices(MIN_NN_FACTOR, name);
        StringBuilder stringBuilder = initStringBuilder();
        int maxNumberOfAnts = data.getProblemSize();
        for (int numberOfAnts = 1; numberOfAnts <= maxNumberOfAnts; numberOfAnts += step) {
            AcoConfig config = configuring.apply(numberOfAnts, localSearch);
            for (int i = 0; i < ITERATIONS; i++) {
                final Solution solution = solving.apply(data, config);
                appendStringBuilder(localSearch, MIN_NN_FACTOR, stringBuilder, numberOfAnts, config, solution);
                System.out.println(((double) (((numberOfAnts - 1) * ITERATIONS) + i) / ((maxNumberOfAnts) * ITERATIONS)) * 100 + "%");
            }
        }
        writeToFile(localSearch, name + "_" + algorithmName, stringBuilder);
    }


    @DataProvider
    public Object[][] incrementNNProvider() {
        return new Object[][]{
                {"berlin52", false, antSystem, acoConfigIncrementNN},//TODO add algo name
                {"berlin52", true, antSystem, acoConfigIncrementNN},
                {"gr202", false, antSystem, acoConfigIncrementNN},
                {"gr202", true, antSystem, acoConfigIncrementNN},
                {"pa561", false, antSystem, acoConfigIncrementNN},
                {"pa561", true, antSystem, acoConfigIncrementNN},
                /*{"dsj1000", false, 20, antSystem, acoConfigIncrementAnts},
                {"dsj1000", true, 20, antSystem, acoConfigIncrementAnts},*/
        };
    }


    @Test(dataProvider = "incrementNNProvider", enabled = false)
    public void testStatsIncrementNN(String name,
                                     boolean localSearch,
                                     BiFunction<StaticData, AcoConfig, Solution> solving,
                                     TriFunction<Integer, Integer, Boolean, AcoConfig> configuring) throws IOException {
        StringBuilder stringBuilder = initStringBuilder();
        for (int nnFactor = MIN_NN_FACTOR; nnFactor <= MAX_NN_FACTOR; nnFactor++) {
            StaticData data = getMatrices(nnFactor, name);
            int antsCount = (int) (data.getProblemSize() * 0.6);
            AcoConfig config = configuring.apply(nnFactor, antsCount, localSearch);
            for (int i = 0; i < ITERATIONS; i++) {
                final Solution solution = solving.apply(data, config);
                appendStringBuilder(localSearch, nnFactor, stringBuilder, antsCount, config, solution);
                System.out.println(((double) (((nnFactor - 15) * ITERATIONS) + i) / ((MAX_NN_FACTOR - MIN_NN_FACTOR) * ITERATIONS)) * 100 + "%");
            }
        }
        writeToFile(localSearch, name + "_nn", stringBuilder);
    }

    @DataProvider
    public Object[][] performanceAntsProvider() {
        return new Object[][]{
                {"gr202", false, 5, 202, antSystem, acoConfigIncrementAnts, "as"},
                {"gr202", true, 5, 202, antSystem, acoConfigIncrementAnts, "as"},
        };
    }

    @Test(dataProvider = "performanceAntsProvider")
    public void testPerformanceForCPUCount(String name,
                                           boolean localSearch,
                                           int step,
                                           double maxNumberOfAnts, BiFunction<StaticData, AcoConfig, Solution> solving,
                                           BiFunction<Integer, Boolean, AcoConfig> configuring,
                                           String algorithmName) throws IOException {

        StringBuilder stringBuilder = initPerfStringBuilder();
        int cpuCount = Runtime.getRuntime().availableProcessors();
        for (int numberOfAnts = 1; numberOfAnts <= maxNumberOfAnts; numberOfAnts += step) {
            AcoConfig config = configuring.apply(numberOfAnts, localSearch);
            for (int i = 0; i < ITERATIONS; i++) {
                long timeMillis = System.currentTimeMillis();
                StaticData data = getMatrices(MIN_NN_FACTOR, name);
                final Solution solution = solving.apply(data, config);
                appendStringBuilder(localSearch, MIN_NN_FACTOR, stringBuilder, numberOfAnts, config, solution,
                        cpuCount, System.currentTimeMillis() - timeMillis);
                System.out.println(((double) (((numberOfAnts - 1) * ITERATIONS) + i) / ((maxNumberOfAnts) * ITERATIONS)) * 100 + "%");
            }
        }
        writeToFile(localSearch, "performance_CPU_" + cpuCount + "_" + name + "_" + algorithmName, stringBuilder);
    }

    private StringBuilder initPerfStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CPU_COUNT,TIME_MS,NN_FACTOR,ANT_COUNT,LOCAL_SEARCH,TOUR_LEN,SOLUTION_FOUND_IN_IT,NON_IMPROVED_PERIODS\n");
        return stringBuilder;

    }

    private void appendStringBuilder(boolean localSearch, int nnFactor,
                                     StringBuilder stringBuilder, int numberOfAnts,
                                     AcoConfig config, Solution solution, int cpuCount, long time) {
        stringBuilder.append(cpuCount);
        stringBuilder.append(",");
        stringBuilder.append(time);
        stringBuilder.append(",");
        appendStringBuilder(localSearch, nnFactor, stringBuilder, numberOfAnts, config, solution);
    }


    private void appendStringBuilder(boolean localSearch, int nnFactor,
                                     StringBuilder stringBuilder, int numberOfAnts,
                                     AcoConfig config, Solution solution) {
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

    private StaticData getMatrices(int nnFactor, String name) throws IOException {
        return new StaticDataBuilder(TspLibParser.parseTsp("tsplib/" + name + ".tsp"))
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(nnFactor)
                .build();
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

}
