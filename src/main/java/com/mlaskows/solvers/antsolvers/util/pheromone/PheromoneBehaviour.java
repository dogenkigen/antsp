package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.datamodel.IterationResult;

public interface PheromoneBehaviour {

    void initializePheromone();

    double[][] getChoicesInfo();

    void evaporatePheromone();

    void depositPheromone(IterationResult iterationResult);

}
