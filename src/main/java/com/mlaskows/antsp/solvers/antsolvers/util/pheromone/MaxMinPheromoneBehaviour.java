package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.MaxMinDepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.MaxMinInitializeBehaviour;

public class MaxMinPheromoneBehaviour extends GenericPheromoneBehaviour {

    public MaxMinPheromoneBehaviour(StaticData data,
                                    MaxMinConfig config) {
        super(data, config, new MaxMinDepositBehaviour(config, data.getProblemSize()),
                new MaxMinInitializeBehaviour());
    }

}
