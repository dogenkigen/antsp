package com.mlaskows.antsp.config;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AcoConfig {
    private final int heuristicImportance;
    private final int pheromoneImportance;
    private final double pheromoneEvaporationFactor;
    private final int antsCount;
    private final int nearestNeighbourFactor;
    private final int maxStagnationCount;

    public AcoConfig(int heuristicImportance, int pheromoneImportance,
                     double pheromoneEvaporationFactor, int antsCount,
                     int nearestNeighbourFactor, int maxStagnationCount) {
        this(heuristicImportance, pheromoneImportance, pheromoneEvaporationFactor,
                antsCount, nearestNeighbourFactor, maxStagnationCount, 1);
    }

    public AcoConfig(int heuristicImportance, int pheromoneImportance,
                     double pheromoneEvaporationFactor, int antsCount,
                     int nearestNeighbourFactor, int maxStagnationCount,
                     int minPheromoneLimitDivider) {
        this.heuristicImportance = heuristicImportance;
        this.pheromoneImportance = pheromoneImportance;
        this.pheromoneEvaporationFactor = pheromoneEvaporationFactor;
        this.antsCount = antsCount;
        this.nearestNeighbourFactor = nearestNeighbourFactor;
        this.maxStagnationCount = maxStagnationCount;
    }

    /**
     * β
     *
     * @return
     */
    public int getHeuristicImportance() {
        return heuristicImportance;
    }

    /**
     * α
     *
     * @return
     */
    public int getPheromoneImportance() {
        return pheromoneImportance;
    }

    /**
     * ρ
     *
     * @return
     */
    public double getPheromoneEvaporationFactor() {
        return pheromoneEvaporationFactor;
    }

    /**
     * m
     *
     * @return
     */
    public int getAntsCount() {
        return antsCount;
    }

    public int getNearestNeighbourFactor() {
        return nearestNeighbourFactor;
    }

    public int getMaxStagnationCount() {
        return maxStagnationCount;
    }

}
