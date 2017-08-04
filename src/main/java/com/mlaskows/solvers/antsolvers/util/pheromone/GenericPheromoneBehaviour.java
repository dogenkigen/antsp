package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.util.pheromone.deposit.DepositBehaviour;
import com.mlaskows.solvers.antsolvers.util.pheromone.init.InitializeBehaviour;

public class GenericPheromoneBehaviour {


    private final PheromoneProcessor pheromoneProcessor;
    private final StaticMatricesHolder matrices;
    private final DepositBehaviour depositBehaviour;
    private final InitializeBehaviour initializeBehaviour;
    private final AcoConfig config;

    public GenericPheromoneBehaviour(StaticMatricesHolder matrices,
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
        initializeBehaviour.initialize(pheromoneProcessor, matrices,config);
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
