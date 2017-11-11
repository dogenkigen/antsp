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

package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.SolutionFactory;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.BaseWithTspTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolverTest implements BaseWithTspTest {

    @Test
    public void testAli535Solution() throws IOException {
        final AcoConfig config = AcoConfigFactory
                .createDefaultAntSystemConfig(535);
        final Solution solution = SolutionFactory.createAntSystemSolution(getTsp("tsplib/ali535.tsp"), config);
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        assertTrue(solution.getTourLength() < 256086);
    }

    @Test
    public void testAtt532Solution() throws IOException {
        final AcoConfig config = AcoConfigFactory
                .createDefaultAntSystemConfig(532);
        final Solution solution = SolutionFactory.createAntSystemSolution(getTsp("tsplib/att532.tsp"), config);

        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        assertTrue(solution.getTourLength() < 33470);
    }

    @Test
    public void testBerlin52Solution() throws IOException {
        // More ants then cities
        final AcoConfig config = AcoConfigFactory
                .createDefaultAntSystemConfig(100);
        final Solution solution = SolutionFactory
                .createAntSystemSolution(getTsp("tsplib/berlin52.tsp"), config);

        assertTrue(solution.getTourLength() < 8314);
    }

}
