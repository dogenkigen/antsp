package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.RankedBasedConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import java.util.List;
import java.util.stream.Collectors;

public class RankBasedAntPheromoneBehaviour extends AntSystemPheromoneBehaviour {

    private final RankedBasedConfig config;

    public RankBasedAntPheromoneBehaviour(StaticMatricesHolder matrices,
                                          RankedBasedConfig config) {
        super(matrices, config);
        this.config = config;
    }

    @Override
    public void depositPheromone(IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        final List<Ant> ants = iterationResult.getSortedAnts()
                .stream()
                .limit(config.getWeight() - 1)
                .collect(Collectors.toList());
        for (int r = 0; r < ants.size(); r++) {
            final Ant ant = ants.get(r);
            final double pheromoneDelta = (double) (config.getWeight() - r)
                    / ant.getTourLength();
            depositAntPheromone(ant, pheromoneDelta);
        }
        depositAntPheromone(bestAntSoFar, (double) config.getWeight()
                / bestAntSoFar.getTourLength());
    }
}
