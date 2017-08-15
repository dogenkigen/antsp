package com.mlaskows.antsp.solvers;

import com.mlaskows.antsp.datamodel.Solution;

/**
 * Created by mlaskows on 24/06/2017.
 */
public interface Solver {
    Solution getSolution();
    void stop();
}
