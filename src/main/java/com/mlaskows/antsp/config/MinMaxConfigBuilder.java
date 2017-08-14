package com.mlaskows.antsp.config;

public class MinMaxConfigBuilder extends AcoConfigBuilder{

    private int minPheromoneLimitDivider;
    private int reinitializationCount;

    public MinMaxConfigBuilder withMinPheromoneLimitDivider(int minPheromoneLimitDivider) {
        this.minPheromoneLimitDivider = minPheromoneLimitDivider;
        return this;
    }

    public MinMaxConfigBuilder withReinitializationCount(int reinitializationCount) {
        this.reinitializationCount = reinitializationCount;
        return this;
    }

    public MinMaxConfig build() {
        return new MinMaxConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, minPheromoneLimitDivider,
                reinitializationCount, withLocalSearch);
    }
}