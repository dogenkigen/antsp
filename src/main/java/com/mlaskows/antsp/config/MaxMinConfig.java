package com.mlaskows.antsp.config;

public class MaxMinConfig extends AcoConfig{

    private final int minPheromoneLimitDivider;
    private final int reinitializationCount;

    public MaxMinConfig(int heuristicImportance, int pheromoneImportance,
                        double pheromoneEvaporationFactor, int antsCount,
                        int nearestNeighbourFactor, int maxStagnationCount,
                        int minPheromoneLimitDivider, int reinitializationCount,
                        boolean withLocalSearch) {
        super(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, withLocalSearch);
        this.minPheromoneLimitDivider = minPheromoneLimitDivider;
        this.reinitializationCount = reinitializationCount;
    }

    /**
     * a
     *
     * @return
     */
    public int getMinPheromoneLimitDivider() {
        return minPheromoneLimitDivider;
    }

    public int getReinitializationCount() {
        return reinitializationCount;
    }

    @Override
    public String toString() {
        return "MaxMinConfig{" +
                "minPheromoneLimitDivider=" + minPheromoneLimitDivider +
                ", reinitializationCount=" + reinitializationCount +
                "} " + super.toString();
    }
}
