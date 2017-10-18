package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.DepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.InitializeBehaviour;

public abstract class GenericPheromoneBehaviour {

    private final PheromoneProcessor pheromoneProcessor;
    private final StaticData data;
    private final DepositBehaviour depositBehaviour;
    private final InitializeBehaviour initializeBehaviour;
    private final AcoConfig config;

    public GenericPheromoneBehaviour(StaticData data,
                                     AcoConfig config,
                                     DepositBehaviour depositBehaviour,
                                     InitializeBehaviour initializeBehaviour) {
        this.pheromoneProcessor = new PheromoneProcessor(data, config);
        this.data = data;
        this.config = config;
        this.depositBehaviour = depositBehaviour;
        this.initializeBehaviour = initializeBehaviour;
    }

    public void initializePheromone() {
        initializeBehaviour.initialize(pheromoneProcessor, data, config);
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
