package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.MinMaxConfig;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.MinMaxPheromoneBehaviour;

public class MinMaxAntSolver extends GenericAntSolver {

    public MinMaxAntSolver(StaticMatrices matrices, MinMaxConfig config) {
        super(config, new IterationResultFactory(matrices, config),
                new MinMaxPheromoneBehaviour(matrices, config));
    }

}
