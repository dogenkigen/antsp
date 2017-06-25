package com.mlaskows.config;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AcoConfigFactory {

    public static AcoConfig createDefaultAntSystemConfig(int problemSize) {
        // TODO NN can be between 15 and 40. Consider options
        return createAntSystemConfig(3, 1, 0.5, problemSize, 15);
    }

    public static AcoConfig createAntSystemConfig(int heuristicImportance,
                                                  int pheromoneImportance,
                                                  double pheromoneEvaporationFactor,
                                                  int problemSize,
                                                  int nearestNeighbourFactor) {
        return new AcoConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, problemSize, nearestNeighbourFactor);
    }

}
