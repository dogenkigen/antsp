package com.mlaskows.antsp.solvers.heuristic;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.solvers.Solver;

/**
 * Implementation of Nearest Neighbor, O(n^2)
 *
 * @author Maciej Laskowski
 */
public class NearestNeighbourSolver implements Solver {

    private final int[][] distanceMatrix;
    private volatile boolean shouldStop;

    public NearestNeighbourSolver(StaticData data) {
        this.distanceMatrix = data.getDistanceMatrix();
    }

    public NearestNeighbourSolver(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    @Override
    public Solution getSolution() {
        int index = 0;
        int problemSize = distanceMatrix.length;
        final Ant ant = new Ant(problemSize, index);
        for (int i = 0; i < problemSize - 1; i++) {
            if (shouldStop) {
                return null;
            }
            int distance = Integer.MAX_VALUE;
            final int[] row = this.distanceMatrix[index];
            for (int j = 0; j < row.length; j++) {
                if (row[j] < distance && ant.notVisited(j)) {
                    distance = row[j];
                    index = j;
                }
            }
            ant.visit(index, distance);
        }
        final int firstIndex = ant.getFirstIndex();
        ant.visit(firstIndex, distanceMatrix[ant.getCurrentIndex()][firstIndex]);
        return new Solution(ant.getTour(), ant.getTourLength());
    }

    @Override
    public void stop() {
        shouldStop = true;
    }

}
