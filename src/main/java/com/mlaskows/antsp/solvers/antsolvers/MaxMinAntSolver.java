package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.MaxMinPheromoneBehaviour;

public class MaxMinAntSolver extends GenericAntSolver {

    public MaxMinAntSolver(StaticData data, MaxMinConfig config) {
        super(data, config, new IterationResultFactory(data, config),
                new MaxMinPheromoneBehaviour(data, config));
    }

}
