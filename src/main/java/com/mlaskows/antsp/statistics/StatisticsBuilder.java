package com.mlaskows.antsp.statistics;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by maciej_laskowski on 13.07.2017.
 */
public class StatisticsBuilder {

    private final Map<Integer, Integer> iterationTourLength = new TreeMap<>();

    public StatisticsBuilder addIterationTourLength(int index, int tourLength) {
        iterationTourLength.put(index, tourLength);
        return this;
    }

    public StatisticsBuilder addIterationTourLength(int tourLength) {
        return addIterationTourLength(iterationTourLength.size(), tourLength);
    }

    public Statistics build() {
        return new Statistics(new TreeMap<>(iterationTourLength));
    }

}
