package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.RankBasedAntPheromoneBehaviour;

public class RankBasedAntSolver extends GenericAntSolver{

    public RankBasedAntSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config, new IterationResultFactory(matrices, config),
                new RankBasedAntPheromoneBehaviour(matrices, config));
    }

}
