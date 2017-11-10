/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    @Override
    public String toString() {
        return "AcoConfig{" +
                "heuristicImportance=" + heuristicImportance +
                ", pheromoneImportance=" + pheromoneImportance +
                ", pheromoneEvaporationFactor=" + pheromoneEvaporationFactor +
                ", antsCount=" + antsCount +
                ", nearestNeighbourFactor=" + nearestNeighbourFactor +
                ", maxStagnationCount=" + maxStagnationCount +
                ", withLocalSearch=" + withLocalSearch +
                '}';
    }
}
