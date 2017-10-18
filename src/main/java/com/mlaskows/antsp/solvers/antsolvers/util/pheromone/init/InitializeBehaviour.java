package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.solvers.heuristic.NearestNeighbourSolver;

public interface InitializeBehaviour {

    void initialize(PheromoneProcessor pheromoneProcessor,
                    StaticData data,
                    AcoConfig config);

    default Solution getNearestNeighbourSolution(StaticData data) {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(data);
        return nearestNeighbourSolver.getSolution();
    }

}
