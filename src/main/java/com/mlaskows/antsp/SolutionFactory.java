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

package com.mlaskows.antsp;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.antsp.solvers.antsolvers.ElitistAntSolver;
import com.mlaskows.antsp.solvers.antsolvers.MaxMinAntSolver;
import com.mlaskows.antsp.solvers.antsolvers.RankBasedAntSolver;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

import java.io.IOException;

import static com.mlaskows.antsp.config.AcoConfigFactory.createDefaultAntSystemConfig;

public class SolutionFactory {

    public static Solution createAntSystemSolution(Tsp tsp, AcoConfig config) {
        return new AntSystemSolver(getAllData(tsp, config), config).getSolution();
    }

    public static Solution createElitistAntSolution(Tsp tsp, AcoConfig config) {
        return new ElitistAntSolver(getAllData(tsp, config), config).getSolution();
    }

    public static Solution createRankBasedAntSolution(Tsp tsp, RankedBasedConfig config) {
        return new RankBasedAntSolver(getAllData(tsp, config), config).getSolution();
    }

    public static Solution createMaxMinAntSolution(Tsp tsp, MaxMinConfig config) {
        return new MaxMinAntSolver(getAllData(tsp, config), config).getSolution();
    }

    private static StaticData getAllData(Tsp tsp, AcoConfig config) {
        return new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withHeuristicSolution()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
    }

}
