package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.AntSystemPheromoneBehaviour;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolver extends GenericAntSolver {

    public AntSystemSolver(StaticData data, AcoConfig config) {
        super(data, config, new IterationResultFactory(data, config),
                new AntSystemPheromoneBehaviour(data, config));
    }

}
