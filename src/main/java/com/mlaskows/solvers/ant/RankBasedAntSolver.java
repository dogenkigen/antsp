package com.mlaskows.solvers.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RankBasedAntSolver extends AntSystemSolver{

    public RankBasedAntSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
    }

    @Override
    protected void depositPheromone() {
        // TODO move this to config which will require config refactor
        final int weight = 6;
        final List<Ant> ants = getSortedAntsStream(weight - 1).collect(toList());
        for (int r = 0; r < ants.size(); r++) {
            final Ant ant = ants.get(r);
            final double pheromoneDelta = (double) (weight - r) / ant.getTourLength();
            depositAntPheromone(ant, pheromoneDelta);
        }
        getBestSoFarAnt().ifPresent(ant -> depositAntPheromone(ant, (double)
                weight / ant.getTourLength()));
    }
}
