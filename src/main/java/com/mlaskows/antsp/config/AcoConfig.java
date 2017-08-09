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
    private final boolean withLocalSearch;

    public AcoConfig(int heuristicImportance, int pheromoneImportance,
                     double pheromoneEvaporationFactor, int antsCount,
                     int nearestNeighbourFactor, int maxStagnationCount,
                     boolean withLocalSearch) {
        this.heuristicImportance = heuristicImportance;
        this.pheromoneImportance = pheromoneImportance;
        this.pheromoneEvaporationFactor = pheromoneEvaporationFactor;
        this.antsCount = antsCount;
        this.nearestNeighbourFactor = nearestNeighbourFactor;
        this.maxStagnationCount = maxStagnationCount;
        this.withLocalSearch = withLocalSearch;
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

    public boolean isWithLocalSearch() {
        return withLocalSearch;
    }

}
