package com.mlaskows.solvers.antsolvers.util.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.heuristic.TwoOptSolver;

import java.util.List;
import java.util.stream.Collectors;

public class MinMaxIterationResultFactory extends IterationResultFactory {

    private final StaticMatricesHolder matrices;

    public MinMaxIterationResultFactory(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
        this.matrices = matrices;
    }

    @Override
    protected List<Ant> constructAntsSolutionSorted(double[][] choicesInfo) {
        return super.constructAntsSolutionSorted(choicesInfo).stream()
                .limit(Runtime.getRuntime().availableProcessors())
                .parallel()
                .map(ant -> new TwoOptSolver(ant.getSolution(), matrices)
                        .getSolution())
                .map(solution -> new Ant(solution))
                .collect(Collectors.toList());
    }
}
