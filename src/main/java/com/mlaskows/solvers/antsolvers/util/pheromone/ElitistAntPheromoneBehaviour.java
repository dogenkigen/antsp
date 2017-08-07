package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.deposit.ElitistDepositBehaviour;
import com.mlaskows.solvers.antsolvers.util.pheromone.init.ElitistInitializeBehaviour;

public class ElitistAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public ElitistAntPheromoneBehaviour(StaticMatricesHolder matrices, AcoConfig config) {
        // TODO add init behaviour
        super(matrices, config, new ElitistDepositBehaviour(matrices
                .getProblemSize()), new ElitistInitializeBehaviour());
    }

}
