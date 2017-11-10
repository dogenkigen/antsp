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

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.exeptions.Reason;
import com.mlaskows.antsp.exeptions.SolutionException;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.MaxMinInitializeBehaviour;

import java.util.concurrent.ThreadLocalRandom;

public class MaxMinDepositBehaviour implements DepositBehaviour {

    private final MaxMinConfig config;
    private final Solution heuristicSolution;
    private double minPheromoneValue;
    private double maxPheromoneValue;
    final double pBestRoot;
    final double avg;

    public MaxMinDepositBehaviour(StaticData data, MaxMinConfig config) {
        this.pBestRoot = root(0.05, data.getProblemSize());
        this.avg = data.getProblemSize() / 2;
        heuristicSolution = data.getHeuristicSolution()
                .orElseThrow(() -> new SolutionException(Reason.NO_HEURISTIC_SOLUTION));
        this.config = config;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor,
                        IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final Ant iterationBestAnt = iterationResult.getIterationBestAnt();
        if (iterationResult.getIterationsWithNoImprovement() == config.getReinitializationCount()) {
            new MaxMinInitializeBehaviour()
                    .initializeForSolution(pheromoneProcessor, config, bestAntSoFar.getSolution());
            return;
        }
        updateMinMax(Math.min(heuristicSolution.getTourLength(), bestAntSoFar.getTourLength()));
        final Ant ant = getAntToDeposit(bestAntSoFar, iterationBestAnt);
        double pheromoneDelta = getPheromoneDelta(ant);
        pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
    }

    private Ant getAntToDeposit(Ant bestAntSoFar, Ant iterationBestAnt) {
        if (bestAntSoFar.getTourSize() < 200) {
            return iterationBestAnt;
        } else {
            return ThreadLocalRandom.current().nextBoolean() ?
                    bestAntSoFar : iterationBestAnt;
        }
    }

    private void updateMinMax(double tourLength) {
        maxPheromoneValue = 1.0 / (config.getPheromoneEvaporationFactor() * tourLength);
        minPheromoneValue = (maxPheromoneValue * (1.0 - pBestRoot)) / ((avg - 1.0) * pBestRoot);
    }

    private double root(double num, double root) {
        return Math.pow(Math.E, Math.log(num) / root);
    }

    private double getPheromoneDelta(Ant ant) {
        final double pheromoneDeltaCandidate = 1.0 / ant.getTourLength();
        double pheromoneDelta = pheromoneDeltaCandidate > maxPheromoneValue ?
                maxPheromoneValue : pheromoneDeltaCandidate;
        pheromoneDelta = pheromoneDelta < minPheromoneValue ?
                minPheromoneValue : pheromoneDeltaCandidate;
        return pheromoneDelta;
    }
}
