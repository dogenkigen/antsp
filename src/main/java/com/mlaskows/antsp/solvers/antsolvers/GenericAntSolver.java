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

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.Solver;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.GenericPheromoneBehaviour;
import com.mlaskows.antsp.statistics.StatisticsBuilder;

import java.util.List;
import java.util.Optional;

public abstract class GenericAntSolver implements Solver {
    private boolean used;
    private final StaticData data;
    private final AcoConfig config;
    private final IterationResultFactory iterationResultFactory;
    private final GenericPheromoneBehaviour pheromoneBehaviour;
    private final StatisticsBuilder statisticsBuilder;
    private volatile boolean shouldStop;

    GenericAntSolver(StaticData data, AcoConfig config,
                     IterationResultFactory iterationResultFactory,
                     GenericPheromoneBehaviour pheromoneBehaviour) {
        this.data = data;
        this.config = config;
        this.iterationResultFactory = iterationResultFactory;
        this.pheromoneBehaviour = pheromoneBehaviour;
        this.statisticsBuilder = new StatisticsBuilder();
    }

    @Override
    public Solution getSolution() {
        if (used) {
            throw new IllegalStateException("Solver was already used");
        }

        used = true;
        IterationResult iterationResult = null;

        pheromoneBehaviour.initializePheromone();
        while (shouldNotTerminate(iterationResult)) {
            iterationResult = getIterationResult();
            statisticsBuilder.addIterationTourLength(iterationResult
                    .getIterationBestAnt().getTourLength());
            updatePheromone(iterationResult);
        }
        return buildSolutionObject(iterationResult);
    }

    private boolean shouldNotTerminate(IterationResult iterationResult) {
        return !shouldStop && (iterationResult == null || iterationResult != null
                && iterationResult.getIterationsWithNoImprovement() < config.getMaxStagnationCount());
    }

    private IterationResult getIterationResult() {
        IterationResult iterationResult;
        final double[][] choicesInfo = pheromoneBehaviour.getChoicesInfo();
        iterationResult =
                iterationResultFactory.createIterationResult(choicesInfo);
        return iterationResult;
    }

    private void updatePheromone(IterationResult iterationResult) {
        pheromoneBehaviour.evaporatePheromone();
        pheromoneBehaviour.depositPheromone(iterationResult);
    }

    private Solution buildSolutionObject(IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        Solution heuristicSolution = data.getHeuristicSolution().get();
        List<Integer> tour;
        int tourLength;
        if (heuristicSolution.getTourLength() <= bestAntSoFar.getTourLength()) {
            tour = heuristicSolution.getTour();
            tourLength = heuristicSolution.getTourLength();
        } else {
            tour = bestAntSoFar.getTour();
            tourLength = bestAntSoFar.getTourLength();
        }
        return new Solution(tour, tourLength, Optional.of(statisticsBuilder.build()));
    }

    @Override
    public void stop() {
        shouldStop = true;
    }

}
