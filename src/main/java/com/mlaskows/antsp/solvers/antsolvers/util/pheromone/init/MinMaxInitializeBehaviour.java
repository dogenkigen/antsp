package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class MinMaxInitializeBehaviour implements InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticMatrices matrices, AcoConfig config) {
        final Solution solution = getNearestNeighbourSolution(matrices);
        initializeForSolution(pheromoneProcessor, config, solution);
    }

    public void initializeForSolution(PheromoneProcessor pheromoneProcessor,
                                      AcoConfig config, Solution solution) {
        final double initialPheromoneValue = (double) 1 /
                config.getPheromoneEvaporationFactor() *
                solution.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }

}
