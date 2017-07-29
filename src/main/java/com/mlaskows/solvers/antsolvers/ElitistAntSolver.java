package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.ElitistAntPheromoneBehaviour;

public class ElitistAntSolver extends GenericAntSolver {

    public ElitistAntSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config, new IterationResultFactory(matrices, config),
                new ElitistAntPheromoneBehaviour(matrices, config));
    }

}
