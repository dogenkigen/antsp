package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.solvers.heuristic.NearestNeighbourSolver;

public interface InitializeBehaviour {
    //TODO config param can be removed and passed in constructors.
    //TODO This will make possible getting rid of casting in some classes
    void initialize(PheromoneProcessor pheromoneProcessor,
                    StaticMatrices matrices,
                    AcoConfig config);

    default Solution getNearestNeighbourSolution(StaticMatrices matrices) {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(matrices);
        return nearestNeighbourSolver.getSolution();
    }

}
