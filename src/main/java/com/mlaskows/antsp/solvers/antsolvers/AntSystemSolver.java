package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.AntSystemPheromoneBehaviour;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolver extends GenericAntSolver {

    public AntSystemSolver(StaticMatrices matrices, AcoConfig config) {
        super(config, new IterationResultBehaviour(matrices, config),
                new AntSystemPheromoneBehaviour(matrices, config));
    }

}
