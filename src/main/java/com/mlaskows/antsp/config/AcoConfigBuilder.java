package com.mlaskows.antsp.config;

public class AcoConfigBuilder {
    protected int heuristicImportance;
    protected int pheromoneImportance;
    protected double pheromoneEvaporationFactor;
    protected int antsCount;
    protected int nearestNeighbourFactor;
    protected int maxStagnationCount;
    protected boolean withLocalSearch;

    public AcoConfigBuilder withHeuristicImportance(int heuristicImportance) {
        this.heuristicImportance = heuristicImportance;
        return this;
    }

    public AcoConfigBuilder withPheromoneImportance(int pheromoneImportance) {
        this.pheromoneImportance = pheromoneImportance;
        return this;
    }

    public AcoConfigBuilder withPheromoneEvaporationFactor(double pheromoneEvaporationFactor) {
        this.pheromoneEvaporationFactor = pheromoneEvaporationFactor;
        return this;
    }

    public AcoConfigBuilder withAntsCount(int antsCount) {
        this.antsCount = antsCount;
        return this;
    }

    public AcoConfigBuilder withNearestNeighbourFactor(int nearestNeighbourFactor) {
        this.nearestNeighbourFactor = nearestNeighbourFactor;
        return this;
    }

    public AcoConfigBuilder withMaxStagnationCount(int maxStagnationCount) {
        this.maxStagnationCount = maxStagnationCount;
        return this;
    }

    public AcoConfigBuilder withWithLocalSearch(boolean withLocalSearch) {
        this.withLocalSearch = withLocalSearch;
        return this;
    }

    public AcoConfig build() {
        return new AcoConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, withLocalSearch);
    }
}