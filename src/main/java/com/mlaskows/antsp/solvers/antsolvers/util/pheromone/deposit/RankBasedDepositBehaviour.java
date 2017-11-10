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

package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class RankBasedDepositBehaviour implements DepositBehaviour {
    private final RankedBasedConfig config;

    public RankBasedDepositBehaviour(RankedBasedConfig config) {
        this.config = config;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final List<Ant> ants = iterationResult.getSortedAnts();
        final int weight = config.getWeight();
        for (int r = 0; r < weight && r < ants.size(); r++) {
            final Ant ant = ants.get(r);
            final double pheromoneDelta = (double) (weight - r)
                    / ant.getTourLength();
            pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
        }
        pheromoneProcessor.depositAntPheromone(bestAntSoFar, (double) weight
                / bestAntSoFar.getTourLength());
    }
}
