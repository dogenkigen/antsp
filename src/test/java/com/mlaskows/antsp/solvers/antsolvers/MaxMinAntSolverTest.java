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
import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.BaseWithTspTest;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import com.mlaskows.antsp.config.AcoConfigFactory;
import com.mlaskows.antsp.statistics.Statistics;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MaxMinAntSolverTest implements BaseWithTspTest {

    @Test
    public void testAli535Solution() throws IOException {
        final Tsp tsp = getTsp("tsplib/ali535.tsp");
        final MaxMinConfig config = AcoConfigFactory
                .createDefaultMaxMinConfig(tsp.getDimension());

        final Solution solution = SolutionFactory.createMaxMinAntSolution(tsp, config);

        List<Integer> nonImprovementPeriods = solution.getStatistics().get()
                .getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());
        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 256086,
                "Actual solution length is" + solution.getTourLength());
    }

    @Test
    public void testAtt532Solution() throws IOException {
        final Tsp tsp = getTsp("tsplib/att532.tsp");
        final MaxMinConfig config =
                AcoConfigFactory.createDefaultMaxMinConfig(tsp.getDimension());
        final Solution solution = SolutionFactory.createMaxMinAntSolution(tsp, config);

        final Statistics statistics = solution.getStatistics().get();
        List<Integer> nonImprovementPeriods = statistics.getNonImprovementPeriods();
        Assert.assertEquals((int) nonImprovementPeriods.get(nonImprovementPeriods.size() - 1),
                config.getMaxStagnationCount());

        // We assume here that solution will be better than for nearest
        // neighbour algorithm.
        Assert.assertTrue(solution.getTourLength() < 33470);
    }
}
