package com.mlaskows.datamodel;

import java.util.List;

/**
 *
 */
public class IterationAntsWithSolution {
    private final List<Ant> sortedAnts;
    private final Ant bestAntSoFar;

    public IterationAntsWithSolution(List<Ant> sortedAnts, Ant bestAntSoFar) {
        this.sortedAnts = sortedAnts;
        this.bestAntSoFar = bestAntSoFar;
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
        return getIterationBestAnt().hasBetterSolutionThen(getIterationBestAnt());
    }
}
