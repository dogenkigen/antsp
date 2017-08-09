package com.mlaskows.antsp.config;

public class MinMaxConfig extends AcoConfig{

    private final int minPheromoneLimitDivider;
    private final int reinitializationCount;

    public MinMaxConfig(int heuristicImportance, int pheromoneImportance,
                        double pheromoneEvaporationFactor, int antsCount,
                        int nearestNeighbourFactor, int maxStagnationCount,
                        int minPheromoneLimitDivider, int reinitializationCount) {
        super(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount);
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
}
