package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.ElitistAntPheromoneBehaviour;

public class ElitistAntSolver extends GenericAntSolver {

    public ElitistAntSolver(StaticData data, AcoConfig config) {
        super(config, new IterationResultFactory(data, config),
                new ElitistAntPheromoneBehaviour(data, config));
    }

}
