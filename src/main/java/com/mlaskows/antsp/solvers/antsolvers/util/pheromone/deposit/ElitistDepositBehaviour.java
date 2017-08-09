package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.datamodel.Ant;

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
