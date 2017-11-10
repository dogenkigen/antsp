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

public class RankedBasedConfigBuilder extends AcoConfigBuilder {

    private int weight;

    public RankedBasedConfigBuilder() {

    }

    public RankedBasedConfigBuilder(RankedBasedConfig config) {
        super(config);
        this.weight = config.getWeight();
    }

    public RankedBasedConfigBuilder withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public RankedBasedConfig build() {
        return new RankedBasedConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, weight, withLocalSearch);
    }
}
