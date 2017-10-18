package com.mlaskows.antsp;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.datamodel.data.StaticDataBuilder;
import com.mlaskows.antsp.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.tsplib.parser.TspLibParser;
import com.mlaskows.tsplib.datamodel.item.Tsp;

import java.io.IOException;

import static com.mlaskows.antsp.config.AcoConfigFactory.createDefaultAntSystemConfig;

public class SolutionFactory {

    public static Solution getAntSystemSolutionWithDefaultConfig(String pathToTspLibFile)
            throws IOException {
        final Tsp tsp = TspLibParser.parseTsp(pathToTspLibFile);
        final AcoConfig config = createDefaultAntSystemConfig(tsp.getDimension());
        return createAntSystemSolution(config, tsp);
    }

    public static Solution getAntSystemSolution(String pathToTspLibFile,
                                                AcoConfig config)
            throws IOException {
        final Tsp tsp = TspLibParser.parseTsp(pathToTspLibFile);
        return createAntSystemSolution(config, tsp);
    }

    public static Solution createAntSystemSolution(AcoConfig config, Tsp tsp) {
        return new AntSystemSolver(getAllMatrices(config, tsp), config)
                .getSolution();
    }

    private static StaticData getAllMatrices(AcoConfig config, Tsp tsp) {
        return new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
    }

}
