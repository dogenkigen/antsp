package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.solvers.heuristic.NearestNeighbourSolver;

public abstract class InitializeBehaviour {

    public abstract void initialize(PheromoneProcessor pheromoneProcessor,
                                    StaticMatrices matrices,
                                    AcoConfig config);

    protected Solution getNearestNeighbourSolution(StaticMatrices matrices) {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(matrices);
        return nearestNeighbourSolver.getSolution();
    }

}
