package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.AntSystemDepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.AntSystemInitializeBehaviour;

public class AntSystemPheromoneBehaviour extends GenericPheromoneBehaviour {


    public AntSystemPheromoneBehaviour(StaticData data,
                                       AcoConfig config) {
        super(data, config, new AntSystemDepositBehaviour(),
                new AntSystemInitializeBehaviour());
    }

}
