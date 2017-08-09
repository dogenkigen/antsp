package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.MinMaxConfig;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.ant.MinMaxIterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.MinMaxPheromoneBehaviour;

public class MinMaxAntSolver extends GenericAntSolver {

    public MinMaxAntSolver(StaticMatrices matrices, MinMaxConfig config) {
        super(config, new MinMaxIterationResultFactory(matrices, config),
                new MinMaxPheromoneBehaviour(matrices, config));
    }

}
