package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class ElitistInitializeBehaviour extends InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticMatricesHolder matrices, AcoConfig config) {
        final Solution solution = getNearestNeighbourSolution(matrices);
        final double initialPheromoneValue =
                ((config.getAntsCount() + matrices.getProblemSize())
                / config.getPheromoneEvaporationFactor()
                * solution.getTourLength());
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }

}
