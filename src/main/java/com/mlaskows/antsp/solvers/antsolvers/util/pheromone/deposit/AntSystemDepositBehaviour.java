package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.datamodel.Ant;

public class AntSystemDepositBehaviour extends DepositBehaviour {

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        for (Ant ant : iterationResult.getSortedAnts()) {
            pheromoneProcessor
                    .depositAntPheromone(ant, (double) 1 / ant.getTourLength());
        }
    }
}
