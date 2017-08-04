package com.mlaskows.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.config.MinMaxConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

import java.util.SplittableRandom;

public class MinMaxDepositBehaviour extends DepositBehaviour {


    private final SplittableRandom random = new SplittableRandom();
    private final MinMaxConfig config;
    private double minPheromoneValue;
    private double maxPheromoneValue;
    private Ant lastBestAnt;
    private int stagnationCount;

    public MinMaxDepositBehaviour(MinMaxConfig config) {
        this.config = config;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final Ant iterationBestAnt = iterationResult.getIterationBestAnt();
        if (getStagnationCount(bestAntSoFar)
                == config.getReinitializationCount()) {
            reinitializePheromone(pheromoneProcessor, bestAntSoFar);
        }
        updateMinMax(iterationBestAnt);
        final Ant ant = random.nextBoolean() ? iterationBestAnt : bestAntSoFar;
        double pheromoneDelta = getPheromoneDelta(ant);
        pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
        lastBestAnt = bestAntSoFar;
    }

    private void reinitializePheromone(PheromoneProcessor pheromoneProcessor, Ant bestAntSoFar) {
        final double initialPheromoneValue = (double) 1 /
                config.getPheromoneEvaporationFactor() *
                bestAntSoFar.getTourLength();
        pheromoneProcessor.initPheromone(initialPheromoneValue);
    }


    private int getStagnationCount(Ant newBestAntSoFar) {
        if (lastBestAnt != null
                && newBestAntSoFar.getTourLength() == lastBestAnt.getTourLength()) {
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
