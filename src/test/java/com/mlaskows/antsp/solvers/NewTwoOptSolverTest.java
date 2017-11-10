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

package com.mlaskows.antsp.solvers;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.heuristic.NewTwoOptSolver;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class NewTwoOptSolverTest extends BaseOptSolverTest {

    @Test
    public void testBerlin52() throws IOException {
        Tsp tsp = getTsp("tsplib/berlin52.tsp");
        List<Integer> initialTour = getInitialTour(52);
        StaticData dataHolder = new StaticDataBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistance = calculateDistance(dataHolder.getDistanceMatrix(),
                initialTour);

        final NewTwoOptSolver solver = new NewTwoOptSolver
                (new Solution(initialTour, initialDistance), dataHolder);
        final Solution solution = solver.getSolution();

        assertTrue(solution.getTourLength() < initialDistance);
        assertTrue(solution.getTourLength() < 11000);
    }

}
