package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.MaxMinInitializeBehaviour;

import java.util.SplittableRandom;

public class MaxMinDepositBehaviour implements DepositBehaviour {

    private final SplittableRandom random = new SplittableRandom();
    private final MaxMinConfig config;
    private double minPheromoneValue;
    private double maxPheromoneValue;
    private Ant lastBestAnt;
    private int stagnationCount;

    public MaxMinDepositBehaviour(MaxMinConfig config) {
        this.config = config;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor,
                        IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final Ant iterationBestAnt = iterationResult.getIterationBestAnt();
        final int stagnationCount = getStagnationCount(bestAntSoFar);
        if (stagnationCount
                == config.getReinitializationCount()) {
            new MaxMinInitializeBehaviour()
                    .initializeForSolution(pheromoneProcessor, config, bestAntSoFar.getSolution());
        }
        updateMinMax(bestAntSoFar);
        final Ant ant = getAntToDeposit(bestAntSoFar, iterationBestAnt);
        double pheromoneDelta = getPheromoneDelta(ant);
        pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
        lastBestAnt = bestAntSoFar;
    }

    private Ant getAntToDeposit(Ant bestAntSoFar, Ant iterationBestAnt) {
        //return iterationBestAnt;
        // TODO create good balance based on problemSize
        return random.nextBoolean() ? iterationBestAnt : bestAntSoFar;
    }

    private int getStagnationCount(Ant newBestAntSoFar) {
        if (lastBestAnt == null
                || newBestAntSoFar.getTourLength() < lastBestAnt.getTourLength()) {
            stagnationCount = 0;
        } else {
            stagnationCount++;
        }
        return stagnationCount;
    }

    private void updateMinMax(Ant bestSoFarAnt) {
        maxPheromoneValue = (double) 1 /
                config.getPheromoneEvaporationFactor() *
                bestSoFarAnt.getTourLength();
        minPheromoneValue = maxPheromoneValue / config
                .getMinPheromoneLimitDivider();
    }

    private double getPheromoneDelta(Ant ant) {
        final double pheromoneDeltaCandidate = (double) 1 / ant.getTourLength();
        double pheromoneDelta = pheromoneDeltaCandidate > maxPheromoneValue ?
                maxPheromoneValue : pheromoneDeltaCandidate;
        pheromoneDelta = pheromoneDelta < minPheromoneValue ?
                minPheromoneValue : pheromoneDeltaCandidate;
        return pheromoneDelta;
    }
}
