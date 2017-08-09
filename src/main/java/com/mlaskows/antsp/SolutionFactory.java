package com.mlaskows.antsp;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.antsp.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.tsplib.TspLibParser;
import com.mlaskows.tsplib.datamodel.Tsp;

import java.io.IOException;

import static com.mlaskows.antsp.config.AcoConfigFactory.createDefaultAntSystemConfig;

public class SolutionFactory {

    public static Solution getAntSystemSolutionWithDefaultConfig(String pathToTspLibFile)
            throws IOException {
        final Tsp tsp = TspLibParser.parse(pathToTspLibFile);
        final AcoConfig config = createDefaultAntSystemConfig(tsp.getDimension());
        return createAntSystemSolution(config, tsp);
    }

    public static Solution getAntSystemSolution(String pathToTspLibFile,
                                                AcoConfig config)
            throws IOException {
        final Tsp tsp = TspLibParser.parse(pathToTspLibFile);
        return createAntSystemSolution(config, tsp);
    }

    public static Solution createAntSystemSolution(AcoConfig config, Tsp tsp) {
        return new AntSystemSolver(getAllMatrices(config, tsp), config)
                .getSolution();
    }

    private static StaticMatrices getAllMatrices(AcoConfig config, Tsp tsp) {
        return new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
    }

}
