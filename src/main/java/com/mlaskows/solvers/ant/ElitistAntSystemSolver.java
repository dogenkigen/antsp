package com.mlaskows.solvers.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

public class ElitistAntSystemSolver extends AntSystemSolver {

    public ElitistAntSystemSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
    }

    @Override
    protected void updatePheromone() {
        super.updatePheromone();
        getBestSoFarAnt().ifPresent(ant -> depositAntPheromone(ant, (double)
                getProblemSize() / ant.getTourLength()));
    }
}
