package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.RankBasedAntPheromoneBehaviour;

public class RankBasedAntSolver extends GenericAntSolver{

    public RankBasedAntSolver(StaticData data, RankedBasedConfig config) {
        super(data, config, new IterationResultFactory(data, config),
                new RankBasedAntPheromoneBehaviour(data, config));
    }

}
