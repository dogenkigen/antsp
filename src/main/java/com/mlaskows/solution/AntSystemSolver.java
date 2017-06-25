package com.mlaskows.solution;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.matrices.MatricesHolder;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mlaskows on 24/06/2017.
 */
// TODO matrices should be immutable so good idea would be to wrap them into
// class
public class AntSystemSolver implements Solver {

    private final AcoConfig config;
    private final MatricesHolder matrices;
    private final List<Ant> ants;
    private final SplittableRandom random = new SplittableRandom();
    private final double[][] choicesInfo;
    private int problemSize;

    public AntSystemSolver(AcoConfig config, MatricesHolder matrices) {
        this.config = config;
        this.matrices = matrices;
        this.problemSize = matrices.getProblemSize();
        // FIXME constructor shouldn't be so heavy?
        this.ants = getRandomPlacedAnts();

        choicesInfo = getChoicesInfo();
    }

    private double[][] getChoicesInfo() {
        // TODO move to matrices holder?
        final double[][] doubles = new double[problemSize][problemSize];
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                final double choice = Math.pow(matrices.getPheromoneMatrix()[i][j], config
                        .getPheromoneImportance()) * Math.pow(matrices
                        .getHeuristicInformationMatrix()[i][j], config
                        .getHeuristicImportance());
                doubles[i][j] = choice;
                doubles[j][i] = choice;
            }
        }
        return doubles;
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
            // TODO localSearch
        }
        return null;
    }


    private boolean shouldNotTerminate() {
        return false;
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
                matrices.getNearestNeighbors()[currentIndex]);

        final double randomDouble = random.nextDouble(0, sumProbabilities);

        int nextIndex = 1;
        if (sumProbabilities == 0.0) {
            nextIndex = chooseBestNext(ant, currentIndex);
        } else {
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
        // TODO consider performance overhead for streams
        /*Arrays.stream(nearestNeighbors)
                .filter(ant::notVisited)
                .mapToDouble(index -> choicesInfo[currentIndex][index])
                .reduce(0.0, (acc, choice) -> acc += choice);*/
        double sumProbabilities = 0.0;
        for (int j = 0; j < nearestNeighbors.length; j++) {
            if (ant.notVisited(nearestNeighbors[j])) {
                sumProbabilities +=
                        choicesInfo[currentIndex][nearestNeighbors[j]];
            }
        }
        return sumProbabilities;
    }

    private double oldSumProbabilities(Ant ant, double[] doubles) {
        return IntStream.range(0, problemSize)
                .filter(ant::notVisited)
                .mapToDouble(index -> doubles[index])
                .reduce(0.0, (acc, choice) -> {
                    return acc += choice;
                });
    }
}
