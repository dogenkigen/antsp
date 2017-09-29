package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.datamodel.IterationResult;

public interface DepositBehaviour {

    void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult);

}
