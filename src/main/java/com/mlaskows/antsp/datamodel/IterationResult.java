package com.mlaskows.antsp.datamodel;

import java.util.List;

/**
 *
 */
public class IterationResult {

    private final List<Ant> sortedAnts;
    private final Ant bestAntSoFar;
    private final int stagnationCount;

    public IterationResult(List<Ant> sortedAnts, Ant bestAntSoFar, int stagnationCount) {
        this.sortedAnts = sortedAnts;
        this.bestAntSoFar = bestAntSoFar;
        this.stagnationCount = stagnationCount;
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

    public int getStagnationCount() {
        return stagnationCount;
    }

}
