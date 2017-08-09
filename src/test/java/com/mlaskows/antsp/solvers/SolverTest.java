package com.mlaskows.antsp.solvers;

import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.TspLibParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by maciej_laskowski on 05.07.2017.
 */
public interface SolverTest {

    default Tsp getTsp(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName)
                .getFile());
        return TspLibParser.parse(file.getAbsolutePath());
    }

}
