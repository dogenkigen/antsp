package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class AntSystemInitializeBehaviour implements InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticMatrices matrices,
                           AcoConfig config) {
        final Solution solution = getNearestNeighbourSolution(matrices);
        final double initialPheromoneValue =
                (double) config.getAntsCount() / solution.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }
}
