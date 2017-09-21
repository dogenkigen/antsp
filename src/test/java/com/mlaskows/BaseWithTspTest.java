package com.mlaskows;

import com.mlaskows.tsplib.datamodel.Tour;
import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.TspLibParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by maciej_laskowski on 05.07.2017.
 */
public interface BaseWithTspTest {

    default Tsp getTsp(String fileName) throws IOException {
        return TspLibParser.parseTsp(getFileAbsolutePath(fileName));
    }

    default Tour getTour(String fileName) throws IOException {
        return TspLibParser.parseTour(getFileAbsolutePath(fileName));
    }

    default String getFileAbsolutePath(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile())
                .getAbsolutePath();
    }

}
