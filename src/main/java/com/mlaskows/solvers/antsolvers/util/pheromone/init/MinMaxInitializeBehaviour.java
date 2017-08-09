package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class MinMaxInitializeBehaviour extends InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticMatrices matrices, AcoConfig config) {
        final Solution solution = getNearestNeighbourSolution(matrices);
        final double initialPheromoneValue = (double) 1 /
                config.getPheromoneEvaporationFactor() *
                solution.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }
}
