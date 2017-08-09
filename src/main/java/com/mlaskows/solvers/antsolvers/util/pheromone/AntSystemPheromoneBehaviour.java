package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.antsolvers.util.pheromone.deposit.AntSystemDepositBehaviour;
import com.mlaskows.solvers.antsolvers.util.pheromone.init.AntSystemInitializeBehaviour;

public class AntSystemPheromoneBehaviour extends GenericPheromoneBehaviour {


    public AntSystemPheromoneBehaviour(StaticMatrices matrices,
                                       AcoConfig config) {
        super(matrices, config, new AntSystemDepositBehaviour(),
                new AntSystemInitializeBehaviour());
    }

}
