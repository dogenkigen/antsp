package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

public class ElitistAntPheromoneBehaviour extends AntSystemPheromoneBehaviour {

    private final int problemSize;

    public ElitistAntPheromoneBehaviour(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
        problemSize = matrices.getProblemSize();
    }

    @Override
    public void depositPheromone(IterationResult iterationResult) {
        super.depositPheromone(iterationResult);
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        depositAntPheromone(bestAntSoFar,
                (double) problemSize / bestAntSoFar.getTourLength());
    }
}
