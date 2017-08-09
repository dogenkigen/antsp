package com.mlaskows.antsp.datamodel;

import com.mlaskows.antsp.statistics.Statistics;

import java.util.List;
import java.util.Optional;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class Solution {

    private final List<Integer> tour;
    private final int tourLength;
    private final Optional<Statistics> statistics;

    public Solution(List<Integer> tour, int tourLength) {
        this(tour, tourLength, Optional.empty());
    }

    public Solution(List<Integer> tour, int tourLength,
                    Optional<Statistics> statistics) {
        this.tour = tour;
        this.tourLength = tourLength;
        this.statistics = statistics;
    }

    public List<Integer> getTour() {
        return tour;
    }

    public int getTourLength() {
        return tourLength;
    }

    public Optional<Statistics> getStatistics() {
        return statistics;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "tour=" + tour +
                ", tourLength=" + tourLength +
                '}';
    }
}
