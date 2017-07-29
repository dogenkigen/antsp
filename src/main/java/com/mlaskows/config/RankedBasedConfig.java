package com.mlaskows.config;

public class RankedBasedConfig extends AcoConfig {

    private final int weight;

    public RankedBasedConfig(int heuristicImportance, int pheromoneImportance,
                             double pheromoneEvaporationFactor, int antsCount,
                             int nearestNeighbourFactor, int maxStagnationCount,
                             int weight) {
        super(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
