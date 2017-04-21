package com.mlaskows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mlaskows on 20/04/2017.
 */
public class MatricesFactory {
    private List<City> cities;
    private int distanceMatrix[][];
    private int nearestNeighborList[][];

    //double heuristicInformationMatrix[][];
    private MatricesFactory(String pathToTSPLIB) {
        try (Stream<String> stream = Files.lines(Paths.get(pathToTSPLIB))) {
            cities = stream
                    .map(s -> getSplitted(s))
                    .map(sa -> mapToCity(sa))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            // TODO add proper logging
            e.printStackTrace();
            // TODO rethrow the exception
        }
    }

    /**
     * Takes as a param {@link String} with not known number of whitespaces
     * and 3 values. Returns array of 3 values represented as {@link String}
     *
     * @param s {@link String} with not known number of whitespaces
     *          and 3 values
     * @return array of 3 values represented as {@link String}
     */
    private String[] getSplitted(String s) {
        return s.trim().split("\\s+");
    }

    private City mapToCity(String[] values) {
        return new City(Integer.valueOf(values[0]), Double.valueOf(values[1]),
                Double.valueOf(values[2]));
    }

}
