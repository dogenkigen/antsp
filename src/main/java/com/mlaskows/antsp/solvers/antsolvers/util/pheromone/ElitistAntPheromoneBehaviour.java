package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.ElitistInitializeBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.ElitistDepositBehaviour;

public class ElitistAntPheromoneBehaviour extends GenericPheromoneBehaviour {

    public ElitistAntPheromoneBehaviour(StaticData data, AcoConfig config) {
        super(data, config, new ElitistDepositBehaviour(data
                .getProblemSize()), new ElitistInitializeBehaviour());
    }

}
