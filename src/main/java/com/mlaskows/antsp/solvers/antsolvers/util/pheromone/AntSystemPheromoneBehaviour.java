package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.AntSystemDepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.AntSystemInitializeBehaviour;

public class AntSystemPheromoneBehaviour extends GenericPheromoneBehaviour {


    public AntSystemPheromoneBehaviour(StaticMatrices matrices,
                                       AcoConfig config) {
        super(matrices, config, new AntSystemDepositBehaviour(),
                new AntSystemInitializeBehaviour());
    }

}
