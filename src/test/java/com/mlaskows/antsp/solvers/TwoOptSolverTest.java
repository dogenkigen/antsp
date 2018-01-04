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

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.heuristic.TwoOptSolver;
import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolverTest extends BaseOptSolverTest {
    @Test
    public void testAustraliaSolution() throws IOException {
        Tsp tsp = getTsp("tsplib/australia.tsp");

        StaticData dataHolder = new StaticDataBuilder(tsp)
                .withNearestNeighbors(5)
                .build();
        final List<Integer> initialTour = List.of(0, 1, 2, 3, 4, 5);
        int initialDistnace = calculateDistance(dataHolder.getDistanceMatrix(),
                initialTour);

        final Solution solution = computeSolution(initialTour, dataHolder, initialDistnace);

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 3, 5);
        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 7194);
    }

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("tsplib/ali535.tsp");

        StaticData dataHolder = new StaticDataBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        final List<Integer> initialTour = getInitialTour(535);
        int initialDistnace = calculateDistance(dataHolder.getDistanceMatrix(),
                initialTour);

        final Solution solution = computeSolution(initialTour, dataHolder, initialDistnace);

        Assert.assertTrue(solution.getTourLength() < initialDistnace);
        Assert.assertEquals(solution.getTourLength(), 3360076);
    }

    @Test
    public void testBerlin52() throws IOException {
        Tsp tsp = getTsp("tsplib/berlin52.tsp");
        List<Integer> initialTour = getInitialTour(52);
        StaticData dataHolder = new StaticDataBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistnace = calculateDistance(dataHolder.getDistanceMatrix(),
                initialTour);

        final Solution solution = computeSolution(initialTour, dataHolder, initialDistnace);

        Assert.assertTrue(solution.getTourLength() < initialDistnace);
    }

    @Test(enabled = false)
    public void testUsa13509() throws IOException {
        Tsp tsp = getTsp("tsplib/usa13509.tsp");
        List<Integer> initialTour = getInitialTour(13509);

        StaticData dataHolderWithNN = new StaticDataBuilder(tsp)
                .withNearestNeighbors(20)
                .build();
        int initialDistnace = calculateDistance(dataHolderWithNN.getDistanceMatrix(),
                initialTour);

        long nnStartTime = System.currentTimeMillis();
        final Solution solutionWithNN = computeSolution(initialTour, dataHolderWithNN, initialDistnace);
        long nnTime = System.currentTimeMillis() - nnStartTime;

        System.out.println("NN test done in " + nnTime + " ms and solution " +
                "len " + solutionWithNN.getTourLength());

        StaticData dataHolder = new StaticDataBuilder(tsp).build();

        long startTime = System.currentTimeMillis();
        Solution solution = computeSolution(initialTour, dataHolder, initialDistnace);
        long time = System.currentTimeMillis() - startTime;

        System.out.println("no NN test done in " + time + " ms and solution" +
                "len " + solution.getTourLength());

        Assert.assertTrue(solution.getTourLength() < solutionWithNN.getTourLength());
    }

    private Solution computeSolution(List<Integer> initialTour, StaticData
            dataHolder, int initialDistnace) {
        final TwoOptSolver solver = new TwoOptSolver
                (new Solution(initialTour, initialDistnace), dataHolder);
        return solver.getSolution();
    }

}
