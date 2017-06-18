package com.mlaskows;

import java.util.stream.IntStream;

/**
 * Created by mlaskows on 18/06/2017.
 */
//TODO this will require refactoring with different methods for different
// algorithms
public class InitialPheromoneValueFactory {

    private final int distanceMatrix[][];
    private int heuristicLength;

    public InitialPheromoneValueFactory(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public double calculateInitialPheromoneValue(AlgorithmType algorithmType) {
        if (heuristicLength == 0) {
            heuristicLength = calculateHeuristicLength();
        }
        switch (algorithmType) {
            case ANT_SYSTEM:
                return (double) distanceMatrix.length / heuristicLength;
            default:
                return 0.0;
        }


    }

    private int calculateHeuristicLength() {
        // Implementation of Nearest Neighbor, O(n^2)
        final int size = distanceMatrix.length;
        final Ant ant = new Ant(size);
        final ActualIndexHolder actual = new ActualIndexHolder();
        ant.visit(actual.getIndex(), 0);
        for (int i = 0; i < size - 1; i++) {
            final int[] row = this.distanceMatrix[actual.getIndex()];
            IntStream.range(0, size)
                    .mapToObj(index -> new Tuple(index, row[index]))
                    .filter(tuple -> !ant.isVisited(tuple.getIndex()))
                    .sorted()
                    .findFirst()
                    .ifPresent(tuple -> moveAnt(ant, actual, tuple));
        }
        return ant.getTourLength();
    }

    private void moveAnt(Ant ant, ActualIndexHolder actual, Tuple tuple) {
        ant.visit(tuple.getIndex(), tuple.getDistance());
        actual.setIndex(tuple.getIndex());
    }

    private static class ActualIndexHolder {
        private int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

}
