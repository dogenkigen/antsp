package com.mlaskows.solution;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.matrices.MatricesHolder;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

/**
 * Created by mlaskows on 24/06/2017.
 */
// TODO matrices should be immutable so good idea would be to wrap them into
// class
public class AntSystemSolver implements Solver {

    private final AcoConfig config;
    private final MatricesHolder matrices;
    private final List<Ant> ants;
    private final SplittableRandom splittableRandom = new SplittableRandom();

    public AntSystemSolver(AcoConfig config, MatricesHolder matrices) {
        this.config = config;
        this.matrices = matrices;
        this.ants = getRandomPlacedAnts();
    }

    private List<Ant> getRandomPlacedAnts() {
        return splittableRandom.ints(0, matrices.getProblemSize())
                .limit(config.getAntsCount())
                .mapToObj(position -> new Ant(matrices.getProblemSize(), position))
                .collect(Collectors.toList());
    }

    @Override
    public Solution getSolution() {
        while (shouldNotTerminate()) {
            constructSolution();
        }
        return null;
    }


    private boolean shouldNotTerminate() {
        return false;
    }

    private void constructSolution() {

    }
}
