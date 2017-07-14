package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.exeptions.SolutionException;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.statistics.Statistics;
import com.mlaskows.statistics.StatisticsBuilder;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static com.mlaskows.exeptions.Reason.*;

/**
 * Created by mlaskows on 24/06/2017.
 */
// TODO matrices should be immutable so good idea would be to wrap them into
// class
public class AntSystemSolver implements Solver {

    private final AcoConfig config;
    private final StaticMatricesHolder matrices;
    private List<Ant> ants;
    private Ant bestSoFarAnt;
    private final SplittableRandom random = new SplittableRandom();
    private final StatisticsBuilder statisticsBuilder = new StatisticsBuilder();
    private final double[][] choicesInfo;
    private int problemSize;
    private final double[][] heuristicInformationMatrix;
    private final int[][] distanceMatrix;
    private double[][] pheromoneMatrix;
    private int[][] nearestNeighbors;

    public AntSystemSolver(AcoConfig config, StaticMatricesHolder matrices) {
        this.config = config;
        this.matrices = matrices;
        this.problemSize = matrices.getProblemSize();
        pheromoneMatrix = new double[problemSize][problemSize];
        choicesInfo = new double[problemSize][problemSize];
        distanceMatrix = matrices.getDistanceMatrix();
        // TODO move to java generic exceptions
        heuristicInformationMatrix = matrices.getHeuristicInformationMatrix()
                .orElseThrow(() -> new SolutionException(EMPTY_HEURISTIC_MATRIX));
        nearestNeighbors = matrices.getNearestNeighborsMatrix()
                .orElseThrow(() -> new SolutionException(EMPTY_NN_MATRIX));
        // FIXME constructor shouldn't be so heavy?
        this.ants = getRandomPlacedAnts();
        initPheromone();
    }

    private void initPheromone() {
        final NearestNeighbourSolver nearestNeighbourSolver = new NearestNeighbourSolver(matrices);
        final Solution solution = nearestNeighbourSolver.getSolution();
        final double initialPheromoneValue = (double) distanceMatrix.length /
                solution.getTourLength();
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                updatePheromoneOnEdge(i, j, initialPheromoneValue);
            }
        }
    }

    private List<Ant> getRandomPlacedAnts() {
        return random.ints(0, problemSize)
                .limit(config.getAntsCount())
                .mapToObj(position -> new Ant(problemSize, position))
                .collect(Collectors.toList());
    }

    @Override
    public Solution getSolution() {
        int iterationsWithNoImprovementCount = 0;
        Ant bestAnt;
        while (shouldNotTerminate(iterationsWithNoImprovementCount)) {
            ants = getRandomPlacedAnts();
            computeChoicesInfo();
            constructSolution();
            // TODO localSearch (92, 3.7)
            updatePheromone();
            bestAnt = getBestAnt();
            statisticsBuilder.addIterationTourLength(bestAnt.getTourLength());
            if (bestSoFarAnt == null ||
                    bestAnt.getTourLength() < bestSoFarAnt.getTourLength()) {
                bestSoFarAnt = bestAnt;
                iterationsWithNoImprovementCount = 0;
            } else {
                iterationsWithNoImprovementCount++;
            }
        }
        return bestSoFarAnt.getSolution();
    }

    private boolean shouldNotTerminate(int iterationsWithNoImprovementCount) {
        return iterationsWithNoImprovementCount < config.getMaxStagnationCount();
    }

    private void computeChoicesInfo() {
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

    private void constructSolution() {
        // We should start iterating from 1 since every ant has already
        // visited one city
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

    private void updatePheromone() {
        evaporatePheromone();
        depositAllAntsPheromone();
    }

    private void evaporatePheromone() {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                double value =
                        (1 - config.getPheromoneEvaporationFactor()) *
                                pheromoneMatrix[i][j];
                updatePheromoneOnEdge(i, j, value);
            }
        }
    }

    private void depositAllAntsPheromone() {
        ants.forEach(ant -> depositAntPheromone(ant));
    }

    private void depositAntPheromone(Ant ant) {
        double pheromoneDelta = (double) 1 / ant.getTourLength();
        for (int i = 0; i < ant.getTour().size() - 1; i++) {
            int j = ant.getTour().get(i);
            int l = ant.getTour().get(i + 1);
            updatePheromoneOnEdge(j, l, pheromoneMatrix[j][l] + pheromoneDelta);
        }
    }

    private void updatePheromoneOnEdge(int from, int to, double pheromoneValue) {
        pheromoneMatrix[from][to] = pheromoneValue;
        pheromoneMatrix[to][from] = pheromoneValue;
    }

    private Ant getBestAnt() {
        return ants.stream()
                .reduce((ant, acc) -> ant.getTourLength() < acc.getTourLength() ? ant : acc)
                .orElseThrow(() -> new SolutionException(NO_BEST_ANT));
    }

    public Statistics getStatistics() {
        return statisticsBuilder.build();
    }
}
