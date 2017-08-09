package com.mlaskows.antsp.solvers.antsolvers.util.ant;

import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Ant;
import com.mlaskows.antsp.solvers.heuristic.TwoOptSolver;

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
