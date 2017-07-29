package com.mlaskows.datamodel;

import java.util.List;

/**
 *
 */
public class IterationAntsWithSolution {
    private final List<Ant> sortedAnts;
    private final Ant bestAntSoFar;
    private final boolean isImprovedIteration;

    public IterationAntsWithSolution(List<Ant> sortedAnts, Ant bestAntSoFar, boolean isImprovedIteration) {
        this.sortedAnts = sortedAnts;
        this.bestAntSoFar = bestAntSoFar;
        this.isImprovedIteration = isImprovedIteration;
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

    public boolean isImprovedIteration() {
        return isImprovedIteration;
    }
}
