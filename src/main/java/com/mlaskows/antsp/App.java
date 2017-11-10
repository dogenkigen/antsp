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


import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        final Tsp tsp = getTsp("ali535.tsp");
        final AcoConfig config =
                AcoConfigFactory.createDefaultAntSystemConfig(tsp.getDimension());
        final StaticData data = new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final AntSystemSolver solver = new AntSystemSolver(data, config);
        final Solution solution = solver.getSolution();

        System.out.println(solution);
    }

    static Tsp getTsp(String fileName) throws IOException {
        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName)
                .getFile());
        return TspLibParser.parseTsp(file.getAbsolutePath());
    }

}
