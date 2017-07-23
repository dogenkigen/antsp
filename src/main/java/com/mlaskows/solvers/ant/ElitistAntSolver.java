package com.mlaskows.solvers.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

public class ElitistAntSolver extends AntSystemSolver {

    public ElitistAntSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
    }

    @Override
    protected void depositPheromone() {
        super.depositPheromone();
        getBestSoFarAnt().ifPresent(ant -> depositAntPheromone(ant, (double)
                getProblemSize() / ant.getTourLength()));
    }
}
