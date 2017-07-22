package com.mlaskows.solvers;

import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import java.util.concurrent.ThreadLocalRandom;

import static com.mlaskows.exeptions.Reason.EMPTY_NN_MATRIX;

public class AntMover {

    private final double[][] choicesInfo;
    private final int[][] distanceMatrix;
    private final int[][] nearestNeighbors;
    private final int problemSize;

    public AntMover(StaticMatricesHolder matrices, double[][] choicesInfo) {
        this.choicesInfo = choicesInfo;
        distanceMatrix = matrices.getDistanceMatrix();
        nearestNeighbors = matrices.getNearestNeighborsMatrix()
                .orElseThrow(() -> new IllegalArgumentException(EMPTY_NN_MATRIX.toString()));
        problemSize = matrices.getProblemSize();
    }

    public void moveAnt(Ant ant) {
        final int currentIndex = ant.getCurrent();
        final int nextIndex = getNextIndex(ant, currentIndex);
        ant.visit(nextIndex, distanceMatrix[currentIndex][nextIndex]);
    }

    private int getNextIndex(Ant ant, int currentIndex) {
        //https://en.wikipedia.org/wiki/Fitness_proportionate_selection
        double sumProbabilities = newSumProbabilities(ant, currentIndex,
                nearestNeighbors[currentIndex]);

        int nextIndex = 1;
        // This is true in case when all nearest neighbours are already visited
        if (sumProbabilities == 0.0) {
            nextIndex = chooseBestNext(ant, currentIndex);
        } else {
            final double randomDouble = ThreadLocalRandom.current()
                    .nextDouble(0, sumProbabilities);
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
        return nextIndex;
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

}
