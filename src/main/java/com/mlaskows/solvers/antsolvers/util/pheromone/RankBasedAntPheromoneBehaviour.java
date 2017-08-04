package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.RankedBasedConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.deposit.RankBasedDepositBehaviour;
import com.mlaskows.solvers.antsolvers.util.pheromone.init.AntSystemInitializeBehaviour;

public class RankBasedAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public RankBasedAntPheromoneBehaviour(StaticMatricesHolder matrices,
                                          RankedBasedConfig config) {
        // TODO create dedicated init
        super(matrices, config, new RankBasedDepositBehaviour(config),
                new AntSystemInitializeBehaviour());
    }
}
