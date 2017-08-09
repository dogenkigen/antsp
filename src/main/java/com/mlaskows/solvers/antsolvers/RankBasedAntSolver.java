package com.mlaskows.solvers.antsolvers;

import com.mlaskows.config.RankedBasedConfig;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.solvers.antsolvers.util.pheromone.RankBasedAntPheromoneBehaviour;

public class RankBasedAntSolver extends GenericAntSolver{

    public RankBasedAntSolver(StaticMatrices matrices, RankedBasedConfig config) {
        super(config, new IterationResultFactory(matrices, config),
                new RankBasedAntPheromoneBehaviour(matrices, config));
    }

}
