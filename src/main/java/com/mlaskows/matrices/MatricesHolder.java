package com.mlaskows.matrices;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class MatricesHolder {
    //TODO refactor to optionals
    private final int distanceMatrix[][];
    private final int nearestNeighbors[][];
    private final double heuristicInformationMatrix[][];
    private final double pheromoneMatrix[][];

    public MatricesHolder(int[][] distanceMatrix, int[][] nearestNeighbors, double[][] heuristicInformationMatrix, double[][] pheromoneMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.nearestNeighbors = nearestNeighbors;
        this.heuristicInformationMatrix = heuristicInformationMatrix;
        this.pheromoneMatrix = pheromoneMatrix;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int[][] getNearestNeighbors() {
        return nearestNeighbors;
    }

    public double[][] getHeuristicInformationMatrix() {
        return heuristicInformationMatrix;
    }

    public double[][] getPheromoneMatrix() {
        return pheromoneMatrix;
    }

    public int getProblemSize() {
        return distanceMatrix.length;
    }

    public static class MatricesHolderBuilder {
        private int[][] distanceMatrix;
        private int[][] nearestNeighbors;
        private double[][] heuristicInformationMatrix;
        private double[][] pheromoneMatrix;

        public MatricesHolderBuilder withDistanceMatrix(int[][] distanceMatrix) {
            this.distanceMatrix = distanceMatrix;
            return this;
        }

        public MatricesHolderBuilder withNearestNeighbors(int[][] nearestNeighbors) {
            this.nearestNeighbors = nearestNeighbors;
            return this;
        }

        public MatricesHolderBuilder withHeuristicInformationMatrix(double[][] heuristicInformationMatrix) {
            this.heuristicInformationMatrix = heuristicInformationMatrix;
            return this;
        }

        public MatricesHolderBuilder withPheromoneMatrix(double[][] pheromoneMatrix) {
            this.pheromoneMatrix = pheromoneMatrix;
            return this;
        }

        public MatricesHolder build() {
            return new MatricesHolder(distanceMatrix, nearestNeighbors,
                    heuristicInformationMatrix, pheromoneMatrix);
        }
    }
}
