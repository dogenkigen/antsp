package com.mlaskows.matrices;

import java.util.Optional;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class StaticMatricesHolder {

    private final int distanceMatrix[][];
    private final Optional<int[][]> nearestNeighborsMatrix;
    private final Optional<double[][]> heuristicInformationMatrix;

    public StaticMatricesHolder(int[][] distanceMatrix, int[][] nearestNeighborsMatrix, double[][] heuristicInformationMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.nearestNeighborsMatrix = Optional.ofNullable(nearestNeighborsMatrix);
        this.heuristicInformationMatrix = Optional.ofNullable(heuristicInformationMatrix);
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public Optional<int[][]> getNearestNeighborsMatrix() {
        return nearestNeighborsMatrix;
    }

    public Optional<double[][]> getHeuristicInformationMatrix() {
        return heuristicInformationMatrix;
    }

    public int getProblemSize() {
        return distanceMatrix.length;
    }

}
