package com.mlaskows.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maciej_laskowski on 13.07.2017.
 */
public class Statistics {

    private final Map<Integer, Integer> iterationTourLength;

    public Statistics(Map<Integer, Integer> iterationTourLength) {
        this.iterationTourLength = iterationTourLength;
    }

    public Map<Integer, Integer> getIterationTourLength() {
        return iterationTourLength;
    }

    public int getIterationsCount() {
        return iterationTourLength.size();
    }

    public List<Integer> getNonImprovementPeriods() {
        List<Integer> result = new ArrayList<>();
        int acc = 0;
        int bestVale = Integer.MAX_VALUE;
        for (Integer key : iterationTourLength.keySet()) {
            Integer value = iterationTourLength.get(key);
            if (value >= bestVale) {
                acc++;
            } else {
                if (acc > 0) {
                    result.add(acc);
                }
                bestVale = value;
                acc = 0;
            }
        }
        if (acc > 0) {
            result.add(acc);
        }
        return result;
    }
}
