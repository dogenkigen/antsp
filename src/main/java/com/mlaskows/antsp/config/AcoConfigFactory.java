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
public class AcoConfigFactory {

    public static AcoConfig createDefaultAntSystemConfig(int antsCount) {
        return createAcoConfig(3, 1, 0.5, antsCount, 15, 10, false);
    }

    public static AcoConfigBuilder createAcoConfigBuilderWithDefaults(int antsCount) {
        return new AcoConfigBuilder(createDefaultAntSystemConfig(antsCount));
    }

    public static AcoConfig createDefaultElitistConfig(int antsCount) {
        return createAcoConfig(3, 1, 0.9, antsCount, 15, 10, false);
    }

    public static AcoConfigBuilder createElitistConfigBuilderWithDefaults(int antsCount) {
        return new AcoConfigBuilder(createDefaultElitistConfig(antsCount));
    }

    public static RankedBasedConfig createDefaultRankedBasedConfig(int antsCount) {
        return createRankedBasedConfig(3, 1, 0.1, antsCount, 15, 10,
                6, false);
    }

    public static RankedBasedConfigBuilder createRankBasedConfigBuilderWithDefaults(int antsCount) {
        return new RankedBasedConfigBuilder(createDefaultRankedBasedConfig(antsCount));
    }

    public static MaxMinConfig createDefaultMaxMinConfig(int antsCount) {
        return createMaxMinConfig(3, 1, 0.05, antsCount, 15, 100, 80, true);
    }

    public static MaxMinConfigBuilder createMaxMinConfigBuilderWithDefaults(int antsCount) {
        return new MaxMinConfigBuilder(createDefaultMaxMinConfig(antsCount));
    }

    public static AcoConfig createAcoConfig(int heuristicImportance,
                                            int pheromoneImportance,
                                            double pheromoneEvaporationFactor,
                                            int antsCount,
                                            int nearestNeighbourFactor,
                                            int maxStagnationCount,
                                            boolean withLocalSearch) {
        return new AcoConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, withLocalSearch);
    }

    public static RankedBasedConfig createRankedBasedConfig(int heuristicImportance,
                                                            int pheromoneImportance,
                                                            double pheromoneEvaporationFactor,
                                                            int antsCount,
                                                            int nearestNeighbourFactor,
                                                            int maxStagnationCount,
                                                            int weight,
                                                            boolean withLocalSearch) {
        return new RankedBasedConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount,
                nearestNeighbourFactor, maxStagnationCount, weight,
                withLocalSearch);
    }

    public static MaxMinConfig createMaxMinConfig(int heuristicImportance,
                                                  int pheromoneImportance,
                                                  double pheromoneEvaporationFactor,
                                                  int antsCount,
                                                  int nearestNeighbourFactor,
                                                  int maxStagnationCount,
                                                  int reinitializationCount,
                                                  boolean withLocalSearch) {
        return new MaxMinConfig(heuristicImportance, pheromoneImportance,
                pheromoneEvaporationFactor, antsCount, nearestNeighbourFactor,
                maxStagnationCount, reinitializationCount, withLocalSearch);
    }

}
