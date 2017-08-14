package com.mlaskows.antsp.config;

public class RankedBasedConfig extends AcoConfig {

    private final int weight;

    public RankedBasedConfig(int heuristicImportance, int pheromoneImportance,
                             double pheromoneEvaporationFactor, int antsCount,
                             int nearestNeighbourFactor, int maxStagnationCount,
                             int weight, boolean withLocalSearch) {
        super(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, withLocalSearch);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "RankedBasedConfig{" +
                "weight=" + weight +
                "} " + super.toString();
    }
}
