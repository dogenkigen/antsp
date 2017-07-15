package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.exeptions.SolutionException;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static com.mlaskows.exeptions.Reason.*;

/**
 * Created by mlaskows on 15/07/2017.
 */
public abstract class AbstractAntSolver {

    private final SplittableRandom random = new SplittableRandom();
    private List<Ant> ants;
    private final AcoConfig config;
    private final StaticMatricesHolder matrices;
    private final double[][] choicesInfo;
    private final int problemSize;
    private final double[][] heuristicInformationMatrix;
    private double[][] pheromoneMatrix;
    private int[][] nearestNeighbors;

    protected AbstractAntSolver(AcoConfig config, StaticMatricesHolder matrices) {
        this.config = config;
        this.matrices = matrices;
        problemSize = matrices.getProblemSize();
        pheromoneMatrix = new double[problemSize][problemSize];
        choicesInfo = new double[problemSize][problemSize];
        // TODO move to java generic exceptions
        heuristicInformationMatrix = matrices.getHeuristicInformationMatrix()
                .orElseThrow(() -> new SolutionException(EMPTY_HEURISTIC_MATRIX));
        nearestNeighbors = matrices.getNearestNeighborsMatrix()
                .orElseThrow(() -> new SolutionException(EMPTY_NN_MATRIX));
        initializeRandomPlacedAnts(problemSize);
        initPheromone(calculateInitialPheromoneValue());
    }

    public abstract double calculateInitialPheromoneValue();

    protected void computeChoicesInfo() {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                final double choice =
                        Math.pow(pheromoneMatrix[i][j], config.getPheromoneImportance()) *
                                Math.pow(heuristicInformationMatrix[i][j], config.getHeuristicImportance());
                choicesInfo[i][j] = choice;
                choicesInfo[j][i] = choice;
            }
        }
    }

    protected void constructSolution() {
        // We should start iterating from 1 since every ant has already
        // visited one city during initialization.
        for (int i = 1; i < problemSize; i++) {
            for (Ant ant : ants) {
                decisionRule(ant);
            }
        }
    }

    private void decisionRule(Ant ant) {
        //https://en.wikipedia.org/wiki/Fitness_proportionate_selection
        final int currentIndex = ant.getCurrent();
        double sumProbabilities = newSumProbabilities(ant, currentIndex,
                nearestNeighbors[currentIndex]);

        int nextIndex = 1;
        // This is true in case when all nearest neighbours are already visited
        if (sumProbabilities == 0.0) {
            nextIndex = chooseBestNext(ant, currentIndex);
        } else {
            final double randomDouble = random.nextDouble(0, sumProbabilities);
            double selectionProbability = 0.0;
            for (int j = 0; j < problemSize; j++) {
                selectionProbability +=
                        ant.isVisited(j) ? 0.0 : choicesInfo[currentIndex][j];
                if (randomDouble < selectionProbability) {
                    nextIndex = j;
                    break;
                }
            }
        }
        ant.visit(nextIndex, matrices.getDistanceMatrix()[currentIndex][nextIndex]);
    }

    private int chooseBestNext(Ant ant, int currentIndex) {
        int nextIndex = 1;
        double v = 0.0;
        for (int j = 0; j < problemSize; j++) {
            if (ant.notVisited(j) && choicesInfo[currentIndex][j] > v) {
                nextIndex = j;
                v = choicesInfo[currentIndex][j];
            }
        }
        return nextIndex;
    }

    private double newSumProbabilities(Ant ant, int currentIndex, int[]
            nearestNeighbors) {
        double sumProbabilities = 0.0;
        for (int j = 0; j < nearestNeighbors.length; j++) {
            if (ant.notVisited(nearestNeighbors[j])) {
                sumProbabilities +=
                        choicesInfo[currentIndex][nearestNeighbors[j]];
            }
        }
        return sumProbabilities;
    }

    protected void evaporatePheromone() {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                double value =
                        (1 - config.getPheromoneEvaporationFactor()) *
                                pheromoneMatrix[i][j];
                updatePheromoneOnEdge(i, j, value);
            }
        }
    }

    protected void depositAntPheromone(Ant ant) {
        double pheromoneDelta = (double) 1 / ant.getTourLength();
        for (int i = 0; i < ant.getTour().size() - 1; i++) {
            int j = ant.getTour().get(i);
            int l = ant.getTour().get(i + 1);
            updatePheromoneOnEdge(j, l, pheromoneMatrix[j][l] + pheromoneDelta);
        }
    }

    protected void updatePheromoneOnEdge(int from, int to, double pheromoneValue) {
        pheromoneMatrix[from][to] = pheromoneValue;
        pheromoneMatrix[to][from] = pheromoneValue;
    }

    protected void initPheromone(double initialPheromoneValue) {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                updatePheromoneOnEdge(i, j, initialPheromoneValue);
            }
        }
    }

    protected void initializeRandomPlacedAnts(int antCount) {
        ants = random.ints(0, antCount)
                .limit(config.getAntsCount())
                .mapToObj(position -> new Ant(problemSize, position))
                .collect(Collectors.toList());
    }

    protected Ant getBestAnt() {
        return ants.stream()
                .reduce((ant, acc) -> ant.getTourLength() < acc.getTourLength() ? ant : acc)
                .orElseThrow(() -> new SolutionException(NO_BEST_ANT));
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
}
