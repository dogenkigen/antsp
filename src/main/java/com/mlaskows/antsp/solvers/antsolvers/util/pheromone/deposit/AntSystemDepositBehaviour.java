package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.datamodel.Ant;

public class AntSystemDepositBehaviour implements DepositBehaviour {

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        for (Ant ant : iterationResult.getSortedAnts()) {
            pheromoneProcessor.depositAntPheromone(ant, 1.0 / ant.getTourLength());
        }
    }
}
