package com.mlaskows.antsp.solvers.antsolvers.util.ant;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;

public class IterationResultBehaviour {

    private final IterationResultFactory iterationResultFactory;

    public IterationResultBehaviour(StaticMatrices matrices, AcoConfig config) {
        this.iterationResultFactory = new IterationResultFactory(matrices, config);
    }

    public IterationResult getIterationResult(double[][] choicesInfo) {
        IterationResult iterationResult;
        iterationResult = iterationResultFactory.createIterationResult(choicesInfo);
        return iterationResult;
    }

    protected IterationResultFactory getIterationResultFactory() {
        return iterationResultFactory;
    }
}