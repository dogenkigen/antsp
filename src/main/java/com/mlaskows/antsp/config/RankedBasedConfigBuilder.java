package com.mlaskows.antsp.config;

public class RankedBasedConfigBuilder extends AcoConfigBuilder {

    private int weight;

    public RankedBasedConfigBuilder withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public RankedBasedConfig build() {
        return new RankedBasedConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, weight, withLocalSearch);
    }
}