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
        }
        return null;
    }


    private boolean shouldNotTerminate() {
        return false;
    }

    private void constructSolution() {
      /*  IntStream.range(0, problemSize)
                .forEach(step -> ants.stream()
                        .forEach(ant -> decisionRule(ant)));*/
        // TODO consider performance overhead for streams
        for (int i = 0; i < problemSize; i++) {
            for (Ant ant : ants) {
                decisionRule(ant);
            }
        }
    }

    private void decisionRule(Ant ant) {
        final int currentIndex = ant.getCurrent();
        double sumProbabilities = IntStream.range(0, problemSize)
                .filter(index -> !ant.isVisited(index))
                .mapToDouble(index -> choicesInfo[currentIndex][index])
                .reduce(0, (acc, choice) -> {
                    return acc += choice;
                });
        final double randomDouble = random.nextDouble(0, sumProbabilities);

        int nextIndex = 1;
        double selectionProbability = choicesInfo[currentIndex][nextIndex];
        for (int j = 0; j < problemSize; j++) {
            selectionProbability += choicesInfo[currentIndex][j];
            if (randomDouble > selectionProbability) {
                nextIndex = j;
                break;
            }
        }
        ant.visit(nextIndex, matrices.getDistanceMatrix()[currentIndex][nextIndex]);
    }
}
