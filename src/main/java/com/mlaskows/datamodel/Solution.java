package com.mlaskows.datamodel;

import java.util.List;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class Solution {
    private final List<Integer> tour;
    private final int tourLength;

    public Solution(List<Integer> tour, int tourLength) {
        this.tour = tour;
        this.tourLength = tourLength;
    }

    public List<Integer> getTour() {
        return tour;
    }

    public int getTourLength() {
        return tourLength;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "tour=" + tour +
                ", tourLength=" + tourLength +
                '}';
    }
}
