package com.mlaskows.antsp.config;

public class MaxMinConfigBuilder extends AcoConfigBuilder {

    private int minPheromoneLimitDivider;
    private int reinitializationCount;

    public MaxMinConfigBuilder() {

    }

    public MaxMinConfigBuilder(MaxMinConfig config) {
        super(config);
        this.minPheromoneLimitDivider = config.getMinPheromoneLimitDivider();
        this.reinitializationCount = config.getReinitializationCount();
    }

    public MaxMinConfigBuilder withMinPheromoneLimitDivider(int minPheromoneLimitDivider) {
        this.minPheromoneLimitDivider = minPheromoneLimitDivider;
        return this;
    }

    public MaxMinConfigBuilder withReinitializationCount(int reinitializationCount) {
        this.reinitializationCount = reinitializationCount;
        return this;
    }

    public MaxMinConfig build() {
        return new MaxMinConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, minPheromoneLimitDivider,
                reinitializationCount, withLocalSearch);
    }
}