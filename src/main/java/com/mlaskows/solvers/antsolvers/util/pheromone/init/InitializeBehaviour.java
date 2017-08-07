package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.solvers.heuristic.NearestNeighbourSolver;

public abstract class InitializeBehaviour {

    public abstract void initialize(PheromoneProcessor pheromoneProcessor,
                                    StaticMatricesHolder matrices,
                                    AcoConfig config);

    protected Solution getNearestNeighbourSolution(StaticMatricesHolder matrices) {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(matrices);
        return nearestNeighbourSolver.getSolution();
    }

}
