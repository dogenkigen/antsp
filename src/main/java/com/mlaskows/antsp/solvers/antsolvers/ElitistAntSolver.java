package com.mlaskows.antsp.solvers.antsolvers;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.solvers.antsolvers.util.ant.IterationResultFactory;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.ElitistAntPheromoneBehaviour;

public class ElitistAntSolver extends GenericAntSolver {

    public ElitistAntSolver(StaticMatrices matrices, AcoConfig config) {
        super(config, new IterationResultFactory(matrices, config),
                new ElitistAntPheromoneBehaviour(matrices, config));
    }

}
