package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.antsp.config.RankedBasedConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class RankBasedDepositBehaviour implements DepositBehaviour {
    private final RankedBasedConfig config;

    public RankBasedDepositBehaviour(RankedBasedConfig config) {
        this.config = config;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final List<Ant> ants = iterationResult.getSortedAnts();
        final int weight = config.getWeight();
        for (int r = 0; r < weight && r < ants.size(); r++) {
            final Ant ant = ants.get(r);
            final double pheromoneDelta = (double) (weight - r)
                    / ant.getTourLength();
            pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
        }
        pheromoneProcessor.depositAntPheromone(bestAntSoFar, (double) weight
                / bestAntSoFar.getTourLength());
    }
}
