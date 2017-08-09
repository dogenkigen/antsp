package com.mlaskows.antsp.config;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AcoConfigFactory {

    public static AcoConfig createDefaultAntSystemConfig(int problemSize) {
        return createAcoConfig(3, 1, 0.5, problemSize, 15, 10, false);
    }

    public static AcoConfig createDefaultElitistConfig(int problemSize) {
        return createAcoConfig(3, 1, 0.9, problemSize, 15, 10, false);
    }

    public static RankedBasedConfig createDefaultRankedBasedConfig(int problemSize) {
        return createRankedBasedConfig(3, 1, 0.1, problemSize, 15, 10,
                6, false);
    }

    public static MinMaxConfig createDefaultMinMaxConfig(int problemSize) {
        return createMinMaxConfig(3, 1, 0.02, problemSize, 15, 400, 2,
                30, true);
    }

    public static AcoConfig createAcoConfig(int heuristicImportance,
                                            int pheromoneImportance,
                                            double pheromoneEvaporationFactor,
                                            int problemSize,
                                            int nearestNeighbourFactor,
                                            int maxStagnationCount,
                                            boolean withLocalSearch) {
        return new AcoConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, problemSize, nearestNeighbourFactor,
                maxStagnationCount, withLocalSearch);
    }

    public static RankedBasedConfig createRankedBasedConfig(int heuristicImportance,
                                                            int pheromoneImportance,
                                                            double pheromoneEvaporationFactor,
                                                            int antsCount,
                                                            int nearestNeighbourFactor,
                                                            int maxStagnationCount,
                                                            int weight,
                                                            boolean withLocalSearch) {
        return new RankedBasedConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount,
                nearestNeighbourFactor, maxStagnationCount, weight,
                withLocalSearch);
    }

    public static MinMaxConfig createMinMaxConfig(int heuristicImportance,
                                                  int pheromoneImportance,
                                                  double pheromoneEvaporationFactor,
                                                  int problemSize,
                                                  int nearestNeighbourFactor,
                                                  int maxStagnationCount,
                                                  int minPheromoneLimitDivider,
                                                  int reinitializationCount,
                                                  boolean withLocalSearch) {
        return new MinMaxConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, problemSize, nearestNeighbourFactor,
                maxStagnationCount, minPheromoneLimitDivider,
                reinitializationCount, withLocalSearch);
    }

}
