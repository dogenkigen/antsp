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

package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class MaxMinInitializeBehaviour implements InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticData data, AcoConfig config) {
        final Solution solution = getExtractHeuristicSolution(data);
        initializeForSolution(pheromoneProcessor, config, solution);
    }

    public void initializeForSolution(PheromoneProcessor pheromoneProcessor,
                                      AcoConfig config, Solution solution) {
        final double initialPheromoneValue = 1.0 / (config.getPheromoneEvaporationFactor() * solution.getTourLength());
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }

}
