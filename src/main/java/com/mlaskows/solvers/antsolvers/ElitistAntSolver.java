package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.ElitistAntPheromoneBehaviour;

public class ElitistAntSolver extends GenericAntSolver {

    public ElitistAntSolver(StaticMatrices matrices, AcoConfig config) {
        super(config, new IterationResultFactory(matrices, config),
                new ElitistAntPheromoneBehaviour(matrices, config));
    }

}
