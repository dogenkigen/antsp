package com.mlaskows.datamodel.matrices;

import java.util.Optional;

/**
 * Created by mlaskows on 18/06/2017.
 */
// TODO rename for just static matrices?
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

    /**
     * Matrix with nearest neighbor lists of depth NN_FACTOR
     * In addition to the distance matrix, it is convenient to store for each
     * city a list of its nearest neighbors.
     * <p>
     * The position r of a city j in city i’s nearest-neighbor list
     * nearestNeighbors[i]  is the index of the distance dij in the sorted list di',
     * that is, nearestNeighbors[i][r]  gives the identifier (index) of the r-th
     * nearest city to city i (i.e., nearestNeighbors[i][r]  = j)
     */
    public Optional<int[][]> getNearestNeighborsMatrix() {
        return nearestNeighborsMatrix;
    }

    /**
     * Heuristic information matrix.
     * <p>
     * Can be used to compute choiceInfo value. Since heuristic information
     * never changes this can be done once and reused.
     * <p>
     * The heuristic information nij is typically inversely proportional to
     * the distance between cities i and j, a straightforward choice being
     * nij = 1/dij
     */
    public Optional<double[][]> getHeuristicInformationMatrix() {
        return heuristicInformationMatrix;
    }

    public int getProblemSize() {
        return distanceMatrix.length;
    }

}
