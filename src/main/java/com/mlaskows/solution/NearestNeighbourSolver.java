package com.mlaskows.solution;

import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Tuple;

import java.util.stream.IntStream;

/**
 * Created by mlaskows on 24/06/2017.
 * <p>
 * Implementation of Nearest Neighbor, O(n^2)
 */
public class NearestNeighbourSolver implements Solver {

    private final int[][] distanceMatrix;

    public NearestNeighbourSolver(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    @Override
    public Solution getSolution() {
        final int size = distanceMatrix.length;
        final Ant ant = new Ant(size, 0);
        final ActualIndexHolder actual = new ActualIndexHolder();
        for (int i = 0; i < size - 1; i++) {
            final int[] row = this.distanceMatrix[actual.getIndex()];
            IntStream.range(0, size)
                    .mapToObj(index -> new Tuple(index, row[index]))
                    .filter(tuple -> !ant.isVisited(tuple.getIndex()))
                    .sorted()
                    .findFirst()
                    .ifPresent(tuple -> moveAnt(ant, actual, tuple));
        }
        return new Solution(ant.getTour(), ant.getTourLength());
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
