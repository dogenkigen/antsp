package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.solvers.heuristic.NearestNeighbourSolver;

public class AntSystemInitializeBehaviour extends InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticMatricesHolder matrices,
                           AcoConfig config) {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(matrices);
        final Solution solution = nearestNeighbourSolver.getSolution();
        final double initialPheromoneValue =
                (double) matrices.getProblemSize() / solution.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }
}
