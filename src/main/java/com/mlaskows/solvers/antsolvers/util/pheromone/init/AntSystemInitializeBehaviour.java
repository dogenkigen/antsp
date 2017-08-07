package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class AntSystemInitializeBehaviour extends InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticMatricesHolder matrices,
                           AcoConfig config) {
        // TODO move repeated code to superclass
        final Solution solution = getNearestNeighbourSolution(matrices);
        final double initialPheromoneValue =
                (double) matrices.getProblemSize() / solution.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }
}
