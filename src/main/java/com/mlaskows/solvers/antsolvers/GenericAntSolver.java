package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.IterationAntsWithSolution;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.Solver;
import com.mlaskows.solvers.antsolvers.util.ant.AntsWithSolutionFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneBehaviour;

public class GenericAntSolver implements Solver {
    private final StaticMatricesHolder matrices;
    private final AcoConfig config;
    private final AntsWithSolutionFactory antsWithSolutionFactory;
    private final PheromoneBehaviour pheromoneBehaviour;

    public GenericAntSolver(StaticMatricesHolder matrices, AcoConfig config,
                            AntsWithSolutionFactory antsWithSolutionFactory,
                            PheromoneBehaviour pheromoneBehaviour) {
        this.matrices = matrices;
        this.config = config;
        this.antsWithSolutionFactory = antsWithSolutionFactory;
        this.pheromoneBehaviour = pheromoneBehaviour;
    }

    @Override
    public Solution getSolution() {
        // TODO move this counting iterations to statistics
        // TODO remember that antsWithSolutionFactory holds bestAntSoFar
        // which means calling this method 2 times is not a good idea so
        // either clean the factory state or throw exception if called 2 times
        int iterationsWithNoImprovement = 0;
        IterationAntsWithSolution iterationAntsWithSolution = null;

        pheromoneBehaviour.initializePheromone();
        while (iterationsWithNoImprovement < config.getMaxStagnationCount()) {
            final double[][] choicesInfo = pheromoneBehaviour.getChoicesInfo();
            iterationAntsWithSolution =
                    antsWithSolutionFactory.createIterationAntsWithSolution(choicesInfo);
            pheromoneBehaviour.evaporatePheromone();
            pheromoneBehaviour.depositPheromone(iterationAntsWithSolution);
            if (iterationAntsWithSolution.isImprovedIteration()) {
                iterationsWithNoImprovement = 0;
            } else {
                iterationsWithNoImprovement++;
            }
        }
        return iterationAntsWithSolution.getBestAntSoFar().getSolution();
    }
}
