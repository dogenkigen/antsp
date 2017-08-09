package com.mlaskows.solvers.antsolvers.util.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatrices;
import com.mlaskows.solvers.heuristic.TwoOptSolver;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MinMaxIterationResultFactory extends IterationResultFactory {

    private final StaticMatrices matrices;

    public MinMaxIterationResultFactory(StaticMatrices matrices, AcoConfig config) {
        super(matrices, config);
        this.matrices = matrices;
    }

    @Override
    protected List<Ant> constructAntsSolutionSorted(double[][] choicesInfo) {
        final List<Ant> ants = super.constructAntsSolutionSorted(choicesInfo).stream()
                .limit(Runtime.getRuntime().availableProcessors())
                .parallel()
                .map(ant -> new TwoOptSolver(ant.getSolution(), matrices)
                        .getSolution())
                .map(Ant::new)
                .collect(toList());
        return ants.stream().sorted().collect(toList());
    }
}
