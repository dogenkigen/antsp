package com.mlaskows.solvers.antsolvers.util.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationAntsWithSolution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class AntsWithSolutionFactory {

    private final StaticMatricesHolder matrices;
    private final AcoConfig config;
    private final int problemSize;
    private final SplittableRandom random = new SplittableRandom();
    private Ant bestAntSoFar;

    public AntsWithSolutionFactory(StaticMatricesHolder matrices, AcoConfig config) {
        this.matrices = matrices;
        this.config = config;
        this.problemSize = matrices.getProblemSize();
    }

    public IterationAntsWithSolution createIterationAntsWithSolution(double[][] choicesInfo) {
        final List<Ant> ants = constructAntsSolution(choicesInfo);
        final List<Ant> sortedAnts = ants.stream().sorted().collect(toList());
        final Ant iterationBestAnt = sortedAnts.get(0);
        boolean isImprovedIteration = false;
        if (bestAntSoFar == null
                || iterationBestAnt.hasBetterSolutionThen(bestAntSoFar)) {
            bestAntSoFar = iterationBestAnt;
            isImprovedIteration = true;
        }
        return new IterationAntsWithSolution(sortedAnts, bestAntSoFar, isImprovedIteration);
    }

    private List<Ant> constructAntsSolution(double[][] choicesInfo) {
        AntMover antMover = new AntMover(matrices, choicesInfo);
        // Iterating should be started from 1 since every ant has already
        // visited one city during initialization.
        return getRandomPlacedAnts(config.getAntsCount())
                .parallel()
                .peek(ant ->
                        IntStream.iterate(1, i -> i < problemSize, i -> i + 1)
                                .forEach(i -> antMover.moveAnt(ant))
                )
                .collect(toList());
    }

    private Stream<Ant> getRandomPlacedAnts(int antCount) {
        return random.ints(0, antCount)
                .limit(config.getAntsCount())
                .mapToObj(position -> new Ant(problemSize, position));
    }

}
