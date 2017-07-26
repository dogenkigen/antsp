package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.datamodel.IterationAntsWithSolution;

public interface PheromoneBehaviour {

    void initializePheromone();

    double[][] getChoicesInfo();

    void evaporatePheromone();

    void depositPheromone(IterationAntsWithSolution iterationAntsWithSolution);

}
