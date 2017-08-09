package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.MinMaxConfig;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.pheromone.deposit.MinMaxDepositBehaviour;
import com.mlaskows.solvers.antsolvers.util.pheromone.init.MinMaxInitializeBehaviour;

public class MinMaxPheromoneBehaviour extends GenericPheromoneBehaviour {

    public MinMaxPheromoneBehaviour(StaticMatrices matrices,
                                    MinMaxConfig config) {
        super(matrices, config, new MinMaxDepositBehaviour(config),
                new MinMaxInitializeBehaviour());
    }

}
