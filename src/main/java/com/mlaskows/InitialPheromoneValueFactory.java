package com.mlaskows;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class InitialPheromoneValueFactory {

    private final int distanceMatrix[][];

    public InitialPheromoneValueFactory(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public double calculateInitialPheromoneValue(AlgorithmType algorithmType) {
        // TODO add calculations
        return 0.0;
    }

}
