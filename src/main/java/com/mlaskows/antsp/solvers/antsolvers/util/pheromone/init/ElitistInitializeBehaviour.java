package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class ElitistInitializeBehaviour implements InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticData data, AcoConfig config) {
        final Solution solution = getNearestNeighbourSolution(data);
        final double initialPheromoneValue =
                ((config.getAntsCount() + data.getProblemSize())
                / config.getPheromoneEvaporationFactor()
                * solution.getTourLength());
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }

}
