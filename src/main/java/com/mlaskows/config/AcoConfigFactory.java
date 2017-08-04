package com.mlaskows.config;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AcoConfigFactory {

    public static AcoConfig createDefaultAntSystemConfig(int problemSize) {
        // TODO NN can be between 15 and 40. Consider options
        return createAcoConfig(3, 1, 0.5, problemSize, 15, 10);
    }

    public static RankedBasedConfig createDefaultRankedBasedConfig(int problemSize) {
        return createRankedBasedConfig(3, 1, 0.1, problemSize, 15, 10, 6);
    }

    // TODO do the same as for RankBased
    public static MinMaxConfig createDefaultMinMaxConfig(int problemSize) {
        return createMinMaxConfig(3, 1, 0.1, problemSize, 15, 40, 2, 30);
    }

    public static AcoConfig createAcoConfig(int heuristicImportance,
                                            int pheromoneImportance,
                                            double pheromoneEvaporationFactor,
                                            int problemSize,
                                            int nearestNeighbourFactor,
                                            int maxStagnationCount) {
        return new AcoConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, problemSize, nearestNeighbourFactor,
                maxStagnationCount);
    }

    public static RankedBasedConfig createRankedBasedConfig(int heuristicImportance,
                                                            int pheromoneImportance,
                                                            double pheromoneEvaporationFactor,
                                                            int antsCount,
                                                            int nearestNeighbourFactor,
                                                            int maxStagnationCount,
                                                            int weight) {
        return new RankedBasedConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount,
                nearestNeighbourFactor, maxStagnationCount, weight);
    }

    public static MinMaxConfig createMinMaxConfig(int heuristicImportance,
                                                  int pheromoneImportance,
                                                  double pheromoneEvaporationFactor,
                                                  int problemSize,
                                                  int nearestNeighbourFactor,
                                                  int maxStagnationCount,
                                                  int minPheromoneLimitDivider,
                                                  int reinitializationCount) {
        return new MinMaxConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, problemSize, nearestNeighbourFactor,
                maxStagnationCount, minPheromoneLimitDivider, reinitializationCount);
    }

}
