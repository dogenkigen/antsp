package com.mlaskows.antsp.statistics;

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
        for (Integer value : iterationTourLength.values()) {
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

    public int getIterationIndexForBestSolution() {
        return iterationTourLength.keySet().stream()
                .min((id1, id2) ->  compareToursForGivenIds(id1, id2))
                .get();
    }

    private int compareToursForGivenIds(Integer id1, Integer id2) {
        return iterationTourLength.get(id1)
                .compareTo(iterationTourLength.get(id2));
    }
}
