package com.mlaskows.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public class ElitistDepositBehaviour extends DepositBehaviour {

    private final int problemSize;

    public ElitistDepositBehaviour(int problemSize) {
        this.problemSize = problemSize;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        new AntSystemDepositBehaviour().deposit(pheromoneProcessor,
                iterationResult);
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        pheromoneProcessor.depositAntPheromone(bestAntSoFar,
                (double) problemSize / bestAntSoFar.getTourLength());
    }
}
