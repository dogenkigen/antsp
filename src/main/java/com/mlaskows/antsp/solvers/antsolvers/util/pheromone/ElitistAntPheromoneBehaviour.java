package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.ElitistInitializeBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.ElitistDepositBehaviour;

public class ElitistAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public ElitistAntPheromoneBehaviour(StaticMatrices matrices, AcoConfig config) {
        super(matrices, config, new ElitistDepositBehaviour(matrices
                .getProblemSize()), new ElitistInitializeBehaviour());
    }

}
