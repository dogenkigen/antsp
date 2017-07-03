package com.mlaskows.solvers;

import com.mlaskows.datamodel.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolver implements Solver {

    private final int[][] distanceMatrix;
    private List<Integer> tour;

    public TwoOptSolver(Solution initialSolution, int[][] distanceMatrix) {
        if (initialSolution.getTour().size() != distanceMatrix.length) {
            throw new IllegalArgumentException("Initial solution tour size " +
                    "should be equal to problem size.");
        }
        this.distanceMatrix = distanceMatrix;
        tour = initialSolution.getTour();
    }

    @Override
    public Solution getSolution() {
        boolean wasImproved = true;
        while (wasImproved) {
            wasImproved = tryToImproveDistance();
        }
        return new Solution(tour, getDistance(tour));
    }

    private boolean tryToImproveDistance() {
        for (int from = 1; from < tour.size() - 1; from++) {
            for (int to = from + 1; to < tour.size(); to++) {
                if (isWorthToImprove(tour, from, to)) {
                    tour = twOptSwap(tour, from, to);
                    return true;
                }
            }
        }
        return false;
    }

    //TODO look only into NN list
    private boolean isWorthToImprove(List<Integer> tour, int from, int to) {
        // An observation made by Steiglitz and Weiner
        return distanceMatrix[tour.get(from - 1)][tour.get(from)] >
                distanceMatrix[tour.get(from - 1)][tour.get(to)];
    }

    private List<Integer> twOptSwap(List<Integer> tour, int from, int to) {
        final List<Integer> swappedTour = new ArrayList<>(tour.size());
        swappedTour.addAll(tour.subList(0, from));
        final List<Integer> fromTo = new ArrayList<>(tour.subList(from, to + 1));
        Collections.reverse(fromTo);
        swappedTour.addAll(fromTo);
        swappedTour.addAll(tour.subList(to + 1, tour.size()));
        return swappedTour;
    }

    private int getDistance(List<Integer> tour) {
        int distance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += distanceMatrix[tour.get(i)][tour.get(i + 1)];
        }
        return distance;
    }

}
