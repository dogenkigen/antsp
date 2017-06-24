package com.mlaskows.solution;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.matrices.MatricesHolder;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class AntSystemSolver implements Solver {

    private final AcoConfig config;
    private final MatricesHolder matrices;

    public AntSystemSolver(AcoConfig config, MatricesHolder matrices) {
        this.config = config;
        this.matrices = matrices;
    }

    @Override
    public Solution getSolution() {
        return null;
    }
}
