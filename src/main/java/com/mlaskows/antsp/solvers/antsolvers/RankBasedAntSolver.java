package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.RankBasedAntPheromoneBehaviour;

public class RankBasedAntSolver extends GenericAntSolver{

    public RankBasedAntSolver(StaticMatrices matrices, RankedBasedConfig config) {
        super(config, new IterationResultBehaviour(matrices, config),
                new RankBasedAntPheromoneBehaviour(matrices, config));
    }

}
