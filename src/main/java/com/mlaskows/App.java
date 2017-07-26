package com.mlaskows;


import com.mlaskows.config.AcoConfig;
import com.mlaskows.config.AcoConfigFactory;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesBuilder;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.antsolvers.AntSystemSolver;
import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.TspLibParser;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        final Tsp tsp = getTsp("ali535.tsp");
        final AcoConfig config =
                AcoConfigFactory.createDefaultAntSystemConfig(tsp.getDimension());
        final StaticMatricesHolder matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(config.getNearestNeighbourFactor())
                .build();
        final AntSystemSolver solver = new AntSystemSolver(matrices, config);
        final Solution solution = solver.getSolution();

        System.out.println(solution);
    }

    static Tsp getTsp(String fileName) throws IOException {
        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName)
                .getFile());
        return TspLibParser.parse(file.getAbsolutePath());
    }

}
