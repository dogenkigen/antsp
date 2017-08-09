package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.Solver;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.GenericPheromoneBehaviour;
import com.mlaskows.antsp.statistics.Statistics;
import com.mlaskows.antsp.statistics.StatisticsBuilder;

public class GenericAntSolver implements Solver {
    private boolean used;
    private final AcoConfig config;
    private final IterationResultFactory iterationResultFactory;
    private final GenericPheromoneBehaviour pheromoneBehaviour;
    private final StatisticsBuilder statisticsBuilder;

    public GenericAntSolver(AcoConfig config,
                            IterationResultFactory iterationResultFactory,
                            GenericPheromoneBehaviour pheromoneBehaviour) {
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
        int iterationsWithNoImprovement = 0;
        IterationResult iterationResult = null;

        pheromoneBehaviour.initializePheromone();
        while (iterationsWithNoImprovement < config.getMaxStagnationCount()) {
            iterationResult = getIterationResult();
            statisticsBuilder.addIterationTourLength(iterationResult
                    .getIterationBestAnt().getTourLength());
            updatePheromone(iterationResult);
            if (iterationResult.isImprovedIteration()) {
                iterationsWithNoImprovement = 0;
            } else {
                iterationsWithNoImprovement++;
            }
        }
        return iterationResult.getBestAntSoFar().getSolution();
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

    public Statistics getStatistics() {
        return statisticsBuilder.build();
    }

}
