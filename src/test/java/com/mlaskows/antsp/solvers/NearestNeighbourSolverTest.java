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

import com.mlaskows.BaseWithTspTest;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.heuristic.NearestNeighbourSolver;
import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class NearestNeighbourSolverTest implements BaseWithTspTest {


    @Test
    public void testAustraliaSolution() throws IOException {
        final Solution solution = getSolution("tsplib/australia.tsp");

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 3, 5, 0);

        assertEquals(expectedTour, solution.getTour());
        assertEquals(10107, solution.getTourLength());
    }

    @Test
    public void testAli535Solution() throws IOException {
        final Solution solution = getSolution("tsplib/ali535.tsp");

        assertEquals(256086, solution.getTourLength());
    }

    @Test
    public void testAtt532() throws IOException {
        final Solution solution = getSolution("tsplib/att532.tsp");

        assertEquals(35516, solution.getTourLength());
    }

    @Test
    public void testBerlin52() throws IOException {
        final Solution solution = getSolution("tsplib/berlin52.tsp");

        assertEquals(8980, solution.getTourLength());
    }

    @Test
    public void testPa561() throws IOException {
        final Solution solution = getSolution("tsplib/pa561.tsp");

        assertEquals(3422, solution.getTourLength());
    }

    @Test
    public void testGr202() throws IOException {
        final Solution solution = getSolution("tsplib/gr202.tsp");

        assertEquals(49719, solution.getTourLength());
    }

    private Solution getSolution(String fileName) throws IOException {
        final Tsp tsp = getTsp(fileName);
        StaticData dataHolder = new StaticDataBuilder(tsp).build();

        final NearestNeighbourSolver solver = new NearestNeighbourSolver
                (dataHolder);
        return solver.getSolution();
    }

}
