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

import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.datamodel.Ant;

public class ElitistDepositBehaviour implements DepositBehaviour {

    private final int problemSize;

    public ElitistDepositBehaviour(int problemSize) {
        this.problemSize = problemSize;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        new AntSystemDepositBehaviour().deposit(pheromoneProcessor,
                iterationResult);
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        pheromoneProcessor.depositAntPheromone(bestAntSoFar,
                (double) problemSize / bestAntSoFar.getTourLength());
    }
}
