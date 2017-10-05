package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.MaxMinDepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.MaxMinInitializeBehaviour;

public class MaxMinPheromoneBehaviour extends GenericPheromoneBehaviour {

    public MaxMinPheromoneBehaviour(StaticMatrices matrices,
                                    MaxMinConfig config) {
        super(matrices, config, new MaxMinDepositBehaviour(config),
                new MaxMinInitializeBehaviour());
    }

}
