package com.mlaskows.antsp.config;

public class MaxMinConfigBuilder extends AcoConfigBuilder {

    private int reinitializationCount;

    public MaxMinConfigBuilder() {

    }

    public MaxMinConfigBuilder(MaxMinConfig config) {
        super(config);
        this.reinitializationCount = config.getReinitializationCount();
    }

    public MaxMinConfigBuilder withReinitializationCount(int reinitializationCount) {
        this.reinitializationCount = reinitializationCount;
        return this;
    }

    public MaxMinConfig build() {
        return new MaxMinConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, reinitializationCount, withLocalSearch);
    }
}