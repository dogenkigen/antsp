package com.mlaskows.config;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AcoConfig {
    private final int heuristicImportance;
    private final int pheromoneImportance;
    private final double pheromoneEvaporationFactor;
    private final int antsCount;
    private final int nearestNeighbourFactor;

    public AcoConfig(int heuristicImportance, int pheromoneImportance,
                     double pheromoneEvaporationFactor, int antsCount, int nearestNeighbourFactor) {
        this.heuristicImportance = heuristicImportance;
        this.pheromoneImportance = pheromoneImportance;
        this.pheromoneEvaporationFactor = pheromoneEvaporationFactor;
        this.antsCount = antsCount;
        this.nearestNeighbourFactor = nearestNeighbourFactor;
    }

    /**
     * β
     * @return
     */
    public int getHeuristicImportance() {
        return heuristicImportance;
    }

    /**
     * α
     * @return
     */
    public int getPheromoneImportance() {
        return pheromoneImportance;
    }

    /**
     * ρ
     * @return
     */
    public double getPheromoneEvaporationFactor() {
        return pheromoneEvaporationFactor;
    }

    /**
     * m
     * @return
     */
    public int getAntsCount() {
        return antsCount;
    }

    public int getNearestNeighbourFactor() {
        return nearestNeighbourFactor;
    }
}
