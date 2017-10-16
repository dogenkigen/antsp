package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.MaxMinInitializeBehaviour;

import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public class MaxMinDepositBehaviour implements DepositBehaviour {

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
        if (stagnationCount == config.getReinitializationCount()) {
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
        if (bestAntSoFar.getTourSize() < 200) {
            return iterationBestAnt;
        } else {
            return ThreadLocalRandom.current().nextBoolean() ?
                    bestAntSoFar : iterationBestAnt;
        }
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
        maxPheromoneValue = 1.0 / (config.getPheromoneEvaporationFactor()) * bestSoFarAnt.getTourLength();
        final int problemSize = bestSoFarAnt.getTourSize() - 1;
        final double pBestRoot = root(0.05, problemSize);
        final double avg = problemSize / 2;
        minPheromoneValue = (maxPheromoneValue * (1.0 - pBestRoot)) / ((avg - 1.0) * pBestRoot);
    }

    private double root(double num, double root) {
        return Math.pow(Math.E, Math.log(num) / root);
    }

    private double getPheromoneDelta(Ant ant) {
        final double pheromoneDeltaCandidate = 1.0 / ant.getTourLength();
        double pheromoneDelta = pheromoneDeltaCandidate > maxPheromoneValue ?
                maxPheromoneValue : pheromoneDeltaCandidate;
        pheromoneDelta = pheromoneDelta < minPheromoneValue ?
                minPheromoneValue : pheromoneDeltaCandidate;
        return pheromoneDelta;
    }
}
