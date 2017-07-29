package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import java.util.List;
import java.util.stream.Collectors;

public class RankBasedAntPheromoneBehaviour extends AntSystemPheromoneBehaviour {

    public RankBasedAntPheromoneBehaviour(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
    }

    @Override
    public void depositPheromone(IterationResult iterationResult) {
        final Ant bestAntSoFar = iterationResult.getBestAntSoFar();
        // TODO move this to config which will require config refactor
        final int weight = 6;
        final List<Ant> ants = iterationResult.getSortedAnts()
                .stream()
                .limit(weight - 1)
                .collect(Collectors.toList());
        for (int r = 0; r < ants.size(); r++) {
            final Ant ant = ants.get(r);
            final double pheromoneDelta = (double) (weight - r) / ant.getTourLength();
            depositAntPheromone(ant, pheromoneDelta);
        }
        depositAntPheromone(bestAntSoFar,
                (double) weight / bestAntSoFar.getTourLength());
    }
}
