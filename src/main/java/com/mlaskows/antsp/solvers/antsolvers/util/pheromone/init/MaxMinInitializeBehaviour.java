package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class MaxMinInitializeBehaviour implements InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticData data, AcoConfig config) {
        final Solution solution = getNearestNeighbourSolution(data);
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
