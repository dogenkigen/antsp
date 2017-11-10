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

public class AcoConfigBuilder {

    protected int heuristicImportance;
    protected int pheromoneImportance;
    protected double pheromoneEvaporationFactor;
    protected int antsCount;
    protected int nearestNeighbourFactor;
    protected int maxStagnationCount;
    protected boolean withLocalSearch;

    public AcoConfigBuilder() {

    }

    public AcoConfigBuilder(AcoConfig config) {
        this.heuristicImportance = config.getHeuristicImportance();
        this.pheromoneImportance = config.getPheromoneImportance();
        this.pheromoneEvaporationFactor = config.getPheromoneEvaporationFactor();
        this.antsCount = config.getAntsCount();
        this.nearestNeighbourFactor = config.getNearestNeighbourFactor();
        this.maxStagnationCount = config.getMaxStagnationCount();
        this.withLocalSearch = config.isWithLocalSearch();
    }

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
