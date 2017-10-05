package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.MaxMinPheromoneBehaviour;

public class MaxMinAntSolver extends GenericAntSolver {

    public MaxMinAntSolver(StaticMatrices matrices, MaxMinConfig config) {
        super(config, new IterationResultFactory(matrices, config),
                new MaxMinPheromoneBehaviour(matrices, config));
    }

}
