package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.exeptions.SolutionException;
import com.mlaskows.solvers.antsolvers.util.ant.AntMover;
import com.mlaskows.statistics.Statistics;
import com.mlaskows.statistics.StatisticsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.mlaskows.exeptions.Reason.*;

/**
 * Created by mlaskows on 15/07/2017.
 */
public abstract class AbstractAntSolver {

    private final StatisticsBuilder statisticsBuilder = new StatisticsBuilder();
    private final SplittableRandom random = new SplittableRandom();
    private List<Ant> ants;
    private final AcoConfig config;
    private final StaticMatricesHolder matrices;
    private final int problemSize;

    protected AbstractAntSolver(StaticMatricesHolder matrices, AcoConfig config) {
        this.config = config;
        this.matrices = matrices;
        problemSize = matrices.getProblemSize();
        initializeRandomPlacedAnts(problemSize);
    }

    public abstract double calculateInitialPheromoneValue();

    protected boolean shouldNotTerminate(int iterationsWithNoImprovementCount) {
        return iterationsWithNoImprovementCount < getConfig().getMaxStagnationCount();
    }


    protected void constructSolution(double[][] choicesInfo) {
        AntMover antMover = new AntMover(matrices, choicesInfo);
        // We should start iterating from 1 since every ant has already
        // visited one city during initialization.
        ants.stream()
                .parallel()
                .forEach(ant ->
                        IntStream.iterate(1, i -> i < problemSize, i -> i + 1)
                                .forEach(i -> antMover.moveAnt(ant))
                );
    }


    protected void initializeRandomPlacedAnts(int antCount) {
        ants = random.ints(0, antCount)
                .limit(config.getAntsCount())
                .mapToObj(position -> new Ant(problemSize, position))
                .collect(Collectors.toList());
    }

    protected Ant getBestAnt(List<Ant> ants) {
        return ants.stream()
                .min(Comparator.comparing(Ant::getTourLength))
                .orElseThrow(() -> new SolutionException(NO_BEST_ANT));
    }

    protected Stream<Ant> getSortedAntsStream(int maxSize) {
        return getAnts().stream()
                .sorted()
                .limit(maxSize);
    }

    protected List<Ant> getAnts() {
        return ants;
    }

    protected AcoConfig getConfig() {
        return config;
    }

    protected StaticMatricesHolder getMatrices() {
        return matrices;
    }

    protected int getProblemSize() {
        return problemSize;
    }

    protected StatisticsBuilder getStatisticsBuilder() {
        return statisticsBuilder;
    }

    protected SplittableRandom getRandom() {
        return random;
    }

    public Statistics getStatistics() {
        return statisticsBuilder.build();
    }
}
