package com.mlaskows.matrices;

import java.util.Optional;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class StaticMatricesHolder {

    private final int distanceMatrix[][];
    private final Optional<int[][]> nearestNeighbors;
    private final Optional<double[][]> heuristicInformationMatrix;

    public StaticMatricesHolder(int[][] distanceMatrix, int[][] nearestNeighbors, double[][] heuristicInformationMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.nearestNeighbors = Optional.ofNullable(nearestNeighbors);
        this.heuristicInformationMatrix = Optional.ofNullable(heuristicInformationMatrix);
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public Optional<int[][]> getNearestNeighbors() {
        return nearestNeighbors;
    }

    public Optional<double[][]> getHeuristicInformationMatrix() {
        return heuristicInformationMatrix;
    }

    public int getProblemSize() {
        return distanceMatrix.length;
    }

}
