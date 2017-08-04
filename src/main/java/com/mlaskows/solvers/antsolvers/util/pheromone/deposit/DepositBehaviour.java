package com.mlaskows.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public abstract class DepositBehaviour {

    public abstract void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult);

}
