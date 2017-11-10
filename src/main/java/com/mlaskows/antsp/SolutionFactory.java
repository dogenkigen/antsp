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
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.tsplib.parser.TspLibParser;
import com.mlaskows.tsplib.datamodel.item.Tsp;

import java.io.IOException;

import static com.mlaskows.antsp.config.AcoConfigFactory.createDefaultAntSystemConfig;

public class SolutionFactory {

    public static Solution getAntSystemSolutionWithDefaultConfig(String pathToTspLibFile)
            throws IOException {
        final Tsp tsp = TspLibParser.parseTsp(pathToTspLibFile);
        final AcoConfig config = createDefaultAntSystemConfig(tsp.getDimension());
        return createAntSystemSolution(config, tsp);
    }

    public static Solution getAntSystemSolution(String pathToTspLibFile,
                                                AcoConfig config)
            throws IOException {
        final Tsp tsp = TspLibParser.parseTsp(pathToTspLibFile);
        return createAntSystemSolution(config, tsp);
    }

    public static Solution createAntSystemSolution(AcoConfig config, Tsp tsp) {
        return new AntSystemSolver(getAllData(config, tsp), config)
                .getSolution();
    }

    private static StaticData getAllData(AcoConfig config, Tsp tsp) {
        return new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withHeuristicSolution()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
    }

}
