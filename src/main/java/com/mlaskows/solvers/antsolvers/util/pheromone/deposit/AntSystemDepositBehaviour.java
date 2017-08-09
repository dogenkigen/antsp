package com.mlaskows.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class AntSystemDepositBehaviour extends DepositBehaviour {

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        for (Ant ant : iterationResult.getSortedAnts()) {
            pheromoneProcessor
                    .depositAntPheromone(ant, (double) (1 / ant.getTourLength()));
        }
    }
}
