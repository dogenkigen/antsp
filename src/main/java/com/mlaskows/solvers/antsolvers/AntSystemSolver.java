package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.AntSystemPheromoneBehaviour;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolver extends GenericAntSolver {

    public AntSystemSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config, new IterationResultFactory(matrices, config),
                new AntSystemPheromoneBehaviour(matrices, config));
    }

}
