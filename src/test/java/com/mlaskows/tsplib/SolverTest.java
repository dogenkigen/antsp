package com.mlaskows.tsplib;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.matrices.StaticMatricesBuilder;
import com.mlaskows.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.Solver;
import com.mlaskows.solvers.TwoOptSolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by maciej_laskowski on 05.07.2017.
 */
public interface SolverTest {

    default Item getItem(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName)
                .getFile());
        return TspLibParser.parse(file.getAbsolutePath());
    }

}
