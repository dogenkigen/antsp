package com.mlaskows.antsp.solvers.heuristic;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.Step;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.solvers.Solver;

import java.util.stream.IntStream;

/**
 * Created by mlaskows on 24/06/2017.
 * <p>
 * Implementation of Nearest Neighbor, O(n^2)
 */
public class NearestNeighbourSolver implements Solver {

    private final int[][] distanceMatrix;

    public NearestNeighbourSolver(StaticMatrices matricesHolder) {
        this.distanceMatrix = matricesHolder.getDistanceMatrix();
    }

    @Override
    public Solution getSolution() {
        final int size = distanceMatrix.length;
        final Ant ant = new Ant(size, 0);
        final ActualIndexHolder actual = new ActualIndexHolder();
        for (int i = 0; i < size - 1; i++) {
            final int[] row = this.distanceMatrix[actual.getIndex()];
            IntStream.range(0, size)
                    .mapToObj(index -> new Step(index, row[index]))
                    .filter(step -> !ant.isVisited(step.getTo()))
                    .sorted()
                    .findFirst()
                    .ifPresent(step -> moveAnt(ant, actual, step));
        }
        return new Solution(ant.getTour(), ant.getTourLength());
    }

    private void moveAnt(Ant ant, ActualIndexHolder actual, Step step) {
        ant.visit(step.getTo(), step.getDistance());
        actual.setIndex(step.getTo());
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
