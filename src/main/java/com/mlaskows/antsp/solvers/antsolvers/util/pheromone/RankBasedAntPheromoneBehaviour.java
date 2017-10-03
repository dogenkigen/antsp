package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.RankBasedDepositBehaviour;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.AntSystemInitializeBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.RankBasedInitializeBehaviour;

public class RankBasedAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public RankBasedAntPheromoneBehaviour(StaticMatrices matrices,
                                          RankedBasedConfig config) {
        super(matrices, config, new RankBasedDepositBehaviour(config),
                new RankBasedInitializeBehaviour());
    }

}
