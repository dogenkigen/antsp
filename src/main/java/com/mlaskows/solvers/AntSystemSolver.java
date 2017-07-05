package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.matrices.StaticMatricesHolder;

import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

/**
 * Created by mlaskows on 24/06/2017.
 */
// TODO matrices should be immutable so good idea would be to wrap them into
// class
public class AntSystemSolver implements Solver {

    private final AcoConfig config;
    private final StaticMatricesHolder matrices;
    private final List<Ant> ants;
    private final SplittableRandom random = new SplittableRandom();
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
        // TODO move error messages to new class
        heuristicInformationMatrix = matrices.getHeuristicInformationMatrix()
                .orElseThrow(() -> new IllegalArgumentException("Heuristic matrix can't be empty!"));
        nearestNeighbors = matrices.getNearestNeighborsMatrix()
                .orElseThrow(() -> new IllegalArgumentException("Nearest neighbors matrix can't be empty!"));
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
                pheromoneMatrix[i][j] = initialPheromoneValue;
                pheromoneMatrix[j][i] = initialPheromoneValue;
            }
        }
    }

    private void computeChoicesInfo() {
        // TODO move to matrices holder?
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                final double choice = Math.pow(pheromoneMatrix[i][j], config
                        .getPheromoneImportance()) * Math.pow(heuristicInformationMatrix[i][j],
                        config.getHeuristicImportance());
                choicesInfo[i][j] = choice;
                choicesInfo[j][i] = choice;
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
        while (shouldNotTerminate()) {
            constructSolution();
            //110
            // TODO localSearch (92, 3.7)
            updatePheromone();
        }
        // TODO merge solutions somehow?
        // it doesn't need to be merged. best ant is the solution
        return new Solution(Collections.emptyList(), 0);
    }

    private boolean shouldNotTerminate() {
        return true;
    }

    private void constructSolution() {
        for (int i = 0; i < problemSize; i++) {
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
        if (sumProbabilities == 0.0) {
            nextIndex = chooseBestNext(ant, currentIndex);
        } else {
            final double randomDouble = random.nextDouble(0, sumProbabilities);
            double selectionProbability = choicesInfo[currentIndex][nextIndex];
            for (int j = 0; j < problemSize; j++) {
                selectionProbability += choicesInfo[currentIndex][j];
                if (randomDouble > selectionProbability) {
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
        evaporate();
        ants.forEach(ant -> depositPheromone(ant));
        computeChoicesInfo();
    }

    private void evaporate() {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                pheromoneMatrix[i][j] = (double) (1 - config
                        .getPheromoneEvaporationFactor()) *
                        pheromoneMatrix[i][j];
                pheromoneMatrix[j][i] = pheromoneMatrix[i][j];
            }
        }
    }

    private void depositPheromone(Ant ant) {
        double pheromoneDelta = (double) 1 / ant.getTourLength();
        for (int i = 0; i < ant.getTour().size() - 1; i++) {
            int j = ant.getTour().get(i);
            int l = ant.getTour().get(i + 1);
            pheromoneMatrix[j][l] = pheromoneMatrix[j][l] + pheromoneDelta;
            pheromoneMatrix[l][j] = pheromoneMatrix[j][l];
        }

    }
}
