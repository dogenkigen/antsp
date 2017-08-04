package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.MinMaxConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.ant.MinMaxIterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.MinMaxPheromoneBehaviour;

public class NewMinMaxAntSolver extends GenericAntSolver{

    public NewMinMaxAntSolver(StaticMatricesHolder matrices, MinMaxConfig config) {
        super(matrices, config, new MinMaxIterationResultFactory(matrices, config),
                new MinMaxPheromoneBehaviour(matrices, config));
    }

}
