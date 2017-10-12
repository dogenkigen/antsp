package com.mlaskows.antsp.config;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AcoConfigFactory {

    public static AcoConfig createDefaultAntSystemConfig(int antsCount) {
        return createAcoConfig(3, 1, 0.5, antsCount, 15, 10, false);
    }

    public static AcoConfigBuilder createAcoConfigBuilderWithDefaults(int antsCount) {
        return new AcoConfigBuilder(createDefaultAntSystemConfig(antsCount));
    }

    public static AcoConfig createDefaultElitistConfig(int antsCount) {
        return createAcoConfig(3, 1, 0.9, antsCount, 15, 10, false);
    }

    public static AcoConfigBuilder createElitistConfigBuilderWithDefaults(int antsCount) {
        return new AcoConfigBuilder(createDefaultElitistConfig(antsCount));
    }

    public static RankedBasedConfig createDefaultRankedBasedConfig(int antsCount) {
        return createRankedBasedConfig(3, 1, 0.1, antsCount, 15, 10,
                6, false);
    }

    public static RankedBasedConfigBuilder createRankBasedConfigBuilderWithDefaults(int antsCount) {
        return new RankedBasedConfigBuilder(createDefaultRankedBasedConfig(antsCount));
    }

    public static MaxMinConfig createDefaultMaxMinConfig(int antsCount) {
        return createMinMaxConfig(3, 1, 0.02, antsCount, 15, 40, 2,
                30, true);
    }

    public static MaxMinConfigBuilder createMaxMinConfigBuilderWithDefaults(int antsCount) {
        return new MaxMinConfigBuilder(createDefaultMaxMinConfig(antsCount));
    }

    public static AcoConfig createAcoConfig(int heuristicImportance,
                                            int pheromoneImportance,
                                            double pheromoneEvaporationFactor,
                                            int antsCount,
                                            int nearestNeighbourFactor,
                                            int maxStagnationCount,
                                            boolean withLocalSearch) {
        return new AcoConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
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

    public static MaxMinConfig createMinMaxConfig(int heuristicImportance,
                                                  int pheromoneImportance,
                                                  double pheromoneEvaporationFactor,
                                                  int antsCount,
                                                  int nearestNeighbourFactor,
                                                  int maxStagnationCount,
                                                  int minPheromoneLimitDivider,
                                                  int reinitializationCount,
                                                  boolean withLocalSearch) {
        return new MaxMinConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, minPheromoneLimitDivider,
                reinitializationCount, withLocalSearch);
    }

}
