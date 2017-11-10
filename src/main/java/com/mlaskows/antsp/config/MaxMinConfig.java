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
