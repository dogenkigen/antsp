package com.mlaskows.antsp.datamodel;

import java.util.List;

/**
 *
 */
public class IterationResult {
    private final List<Ant> sortedAnts;
    private final Ant bestAntSoFar;
    private final int iterationsWithNoImprovement;

    public IterationResult(List<Ant> sortedAnts, Ant bestAntSoFar, int iterationsWithNoImprovement) {
        this.sortedAnts = sortedAnts;
        this.bestAntSoFar = bestAntSoFar;
        this.iterationsWithNoImprovement = iterationsWithNoImprovement;
    }

    public List<Ant> getSortedAnts() {
        return sortedAnts;
    }

    public Ant getIterationBestAnt() {
        return sortedAnts.get(0);
    }

    public Ant getBestAntSoFar() {
        return bestAntSoFar;
    }

    public int getIterationsWithNoImprovement() {
        return iterationsWithNoImprovement;
    }
}
