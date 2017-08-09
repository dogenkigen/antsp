package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.solvers.heuristic.NearestNeighbourSolver;

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
