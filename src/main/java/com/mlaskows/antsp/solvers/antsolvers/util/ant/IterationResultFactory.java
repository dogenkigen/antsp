package com.mlaskows.antsp.solvers.antsolvers.util.ant;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.exeptions.Reason;
import com.mlaskows.antsp.solvers.heuristic.NewTwoOptSolver;

import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class IterationResultFactory {

    private final StaticMatrices matrices;
    private final AcoConfig config;
    private final int problemSize;
    private Ant bestAntSoFar;
    private final int[][] distanceMatrix;
    private final int[][] nearestNeighbors;

    public IterationResultFactory(StaticMatrices matrices, AcoConfig config) {
        this.matrices = matrices;
        this.config = config;
        this.problemSize = matrices.getProblemSize();
        distanceMatrix = matrices.getDistanceMatrix();
        nearestNeighbors = matrices.getNearestNeighborsMatrix()
                .orElseThrow(() -> new IllegalArgumentException(Reason.EMPTY_NN_MATRIX.toString()));
    }

    public IterationResult createIterationResult(double[][] choicesInfo) {
        final List<Ant> sortedAnts = constructAntsSolutionsSorted(choicesInfo);
        final Ant iterationBestAnt = sortedAnts.get(0);
        boolean isImprovedIteration = false;
        if (bestAntSoFar == null
                || iterationBestAnt.hasBetterSolutionThen(bestAntSoFar)) {
            bestAntSoFar = iterationBestAnt;
            isImprovedIteration = true;
        }
        return new IterationResult(sortedAnts, bestAntSoFar, isImprovedIteration);
    }

    private List<Ant> constructAntsSolutionsSorted(double[][] choicesInfo) {
        final Stream<Ant> antsStream =
                getRandomPlacedParallelAnts(config.getAntsCount())
                        .peek(ant -> constructAntSolution(choicesInfo, ant));
        final List<Ant> ants;
        if (config.isWithLocalSearch()) {
            ants = antsStream
                    //.limit(Runtime.getRuntime().availableProcessors())
                    .map(ant -> new NewTwoOptSolver(ant.getSolution(), matrices)
                            .getSolution())
                    .map(Ant::new)
                    .collect(toList());
        } else {
            ants = antsStream.collect(toList());
        }

        return ants.stream().sorted().collect(toList());
    }

    private Stream<Ant> getRandomPlacedParallelAnts(int antCount) {
        return IntStream.range(0, antCount)
                .parallel()
                .mapToObj(i -> new Ant(problemSize + 1, getRandomInt(0, problemSize)));
    }

    private void constructAntSolution(double[][] choicesInfo, Ant ant) {
        // Iterating should be started from 1 since every ant has already
        // visited one city during initialization.
        for (int i = 1; i < problemSize; i++) {
            moveAnt(ant, choicesInfo);
        }
        final int firstIndex = ant.getFirstIndex();
        ant.visit(firstIndex, distanceMatrix[ant.getCurrentIndex()][firstIndex]);
    }

    private void moveAnt(Ant ant, double[][] choicesInfo) {
        final int currentIndex = ant.getCurrentIndex();
        final int nextIndex = getNextIndex(ant, currentIndex, choicesInfo);
        ant.visit(nextIndex, distanceMatrix[currentIndex][nextIndex]);
    }

    private int getNextIndex(Ant ant, int currentIndex, double[][] choicesInfo) {
        //https://en.wikipedia.org/wiki/Fitness_proportionate_selection
        double sumProbabilities = newSumProbabilities(ant, currentIndex,
                nearestNeighbors[currentIndex], choicesInfo);

        int nextIndex = 0;
        // This is true in case when all nearest neighbours are already visited
        if (sumProbabilities == 0.0) {
            nextIndex = chooseBestNext(ant, currentIndex, choicesInfo);
        } else {
            final double randomDouble = getRandomDouble(0, sumProbabilities);
            double selectionProbability = 0.0;
            for (int j = 0; j < problemSize; j++) {
                if (ant.notVisited(j)) {
                    selectionProbability += choicesInfo[currentIndex][j];
                }
                if (randomDouble <= selectionProbability) {
                    nextIndex = j;
                    break;
                }
            }
        }
        return nextIndex;
    }

    private double newSumProbabilities(Ant ant, int currentIndex,
                                       int[] nearestNeighbors,
                                       double[][] choicesInfo) {
        double sumProbabilities = 0.0;
        for (int nearestNeighbour : nearestNeighbors) {
            if (ant.notVisited(nearestNeighbour)) {
                sumProbabilities += choicesInfo[currentIndex][nearestNeighbour];
            }
        }
        return sumProbabilities;
    }

    private int chooseBestNext(Ant ant, int currentIndex, double[][] choicesInfo) {
        int nextIndex = 0;
        double v = 0.0;
        for (int j = 0; j < problemSize; j++) {
            if (ant.notVisited(j) && choicesInfo[currentIndex][j] > v) {
                nextIndex = j;
                v = choicesInfo[currentIndex][j];
            }
        }
        return nextIndex;
    }

    private int getRandomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    private double getRandomDouble(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

}
