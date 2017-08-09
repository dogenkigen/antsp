package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.RankBasedDepositBehaviour;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.AntSystemInitializeBehaviour;

public class RankBasedAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public RankBasedAntPheromoneBehaviour(StaticMatrices matrices,
                                          RankedBasedConfig config) {
        // TODO create dedicated init
        super(matrices, config, new RankBasedDepositBehaviour(config),
                new AntSystemInitializeBehaviour());
    }
}
