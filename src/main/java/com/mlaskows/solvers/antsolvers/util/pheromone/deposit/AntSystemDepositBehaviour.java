package com.mlaskows.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class AntSystemDepositBehaviour extends DepositBehaviour {

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        iterationResult.getSortedAnts()
                .forEach(ant ->
                        // TODO it can be refactored to just pass nominator since pheromone
                        // delta is always computed as x / ant.tourLen
                        pheromoneProcessor.depositAntPheromone(ant, (double)
                                1 / ant
                                .getTourLength()));
    }
}
