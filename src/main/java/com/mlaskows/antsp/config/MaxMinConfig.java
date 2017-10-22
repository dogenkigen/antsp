package com.mlaskows.antsp.config;

public class MaxMinConfig extends AcoConfig{

    private final int reinitializationCount;

    public MaxMinConfig(int heuristicImportance, int pheromoneImportance,
                        double pheromoneEvaporationFactor, int antsCount,
                        int nearestNeighbourFactor, int maxStagnationCount,
                        int reinitializationCount, boolean withLocalSearch) {
        super(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, withLocalSearch);
        this.reinitializationCount = reinitializationCount;
    }

    public int getReinitializationCount() {
        return reinitializationCount;
    }

    @Override
    public String toString() {
        return "MaxMinConfig{ reinitializationCount=" + reinitializationCount +
                "} " + super.toString();
    }
}
