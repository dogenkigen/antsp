package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.RankBasedDepositBehaviour;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.RankBasedInitializeBehaviour;

public class RankBasedAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public RankBasedAntPheromoneBehaviour(StaticData data,
                                          RankedBasedConfig config) {
        super(data, config, new RankBasedDepositBehaviour(config),
                new RankBasedInitializeBehaviour());
    }

}
