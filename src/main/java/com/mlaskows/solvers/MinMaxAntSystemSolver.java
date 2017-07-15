package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSystemSolver extends AbstractAntSolver implements Solver {

    public MinMaxAntSystemSolver(AcoConfig config, StaticMatricesHolder matrices) {
        super(config, matrices);
    }

    @Override
    public Solution getSolution() {
        return null;
    }
}
