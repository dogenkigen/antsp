package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.DepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.InitializeBehaviour;

public class GenericPheromoneBehaviour {


    private final PheromoneProcessor pheromoneProcessor;
    private final StaticMatrices matrices;
    private final DepositBehaviour depositBehaviour;
    private final InitializeBehaviour initializeBehaviour;
    private final AcoConfig config;

    public GenericPheromoneBehaviour(StaticMatrices matrices,
                                     AcoConfig config,
                                     DepositBehaviour depositBehaviour,
                                     InitializeBehaviour initializeBehaviour) {
        this.pheromoneProcessor = new PheromoneProcessor(matrices, config);
        this.matrices = matrices;
        this.config = config;
        this.depositBehaviour = depositBehaviour;
        this.initializeBehaviour = initializeBehaviour;
    }

    public void initializePheromone() {
        initializeBehaviour.initialize(pheromoneProcessor, matrices, config);
    }

    public double[][] getChoicesInfo() {
        return pheromoneProcessor.computeChoicesInfo();
    }

    public void evaporatePheromone() {
        pheromoneProcessor.evaporatePheromone();
    }

    public void depositPheromone(IterationResult iterationResult) {
        depositBehaviour.deposit(pheromoneProcessor, iterationResult);
    }

}
