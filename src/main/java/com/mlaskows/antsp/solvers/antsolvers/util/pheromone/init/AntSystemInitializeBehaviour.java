package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class AntSystemInitializeBehaviour implements InitializeBehaviour {

    @Override
    public void initialize(PheromoneProcessor pheromoneProcessor,
                           StaticData data,
                           AcoConfig config) {
        final Solution solution = getExtractHeuristicSolution(data);
        final double initialPheromoneValue = (double) config.getAntsCount() / solution.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }

}
