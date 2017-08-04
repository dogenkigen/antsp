package com.mlaskows.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public abstract class InitializeBehaviour {

    public abstract void initialize(PheromoneProcessor pheromoneProcessor,
                                    StaticMatricesHolder
                                    matrices,
                                    AcoConfig config);

}
