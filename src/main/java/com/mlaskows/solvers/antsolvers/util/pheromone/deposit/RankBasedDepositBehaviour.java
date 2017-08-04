package com.mlaskows.solvers.antsolvers.util.pheromone.deposit;

import com.mlaskows.config.RankedBasedConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.solvers.antsolvers.util.pheromone.PheromoneProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class RankBasedDepositBehaviour extends DepositBehaviour {
    private final RankedBasedConfig config;

    public RankBasedDepositBehaviour(RankedBasedConfig config) {
        this.config = config;
    }

    @Override
    public void deposit(PheromoneProcessor pheromoneProcessor, IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final List<Ant> ants = iterationResult.getSortedAnts()
                .stream()
                .limit(config.getWeight() - 1)
                .collect(Collectors.toList());
        for (int r = 0; r < ants.size(); r++) {
            final Ant ant = ants.get(r);
            final double pheromoneDelta = (double) (config.getWeight() - r)
                    / ant.getTourLength();
            pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
        }
        pheromoneProcessor.depositAntPheromone(bestAntSoFar, (double) config
                .getWeight()
                / bestAntSoFar.getTourLength());
    }
}
