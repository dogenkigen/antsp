package com.mlaskows.solvers;

import com.mlaskows.datamodel.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolver implements Solver {

    private final Solution initialSolution;
    private final int[][] distanceMatrix;
    private List<Integer> tour;

    public TwoOptSolver(Solution initialSolution, int[][] distanceMatrix) {
        if (initialSolution.getTour().size() != distanceMatrix.length) {
            throw new IllegalArgumentException("Initial solution tour size " +
                    "should be equal to problem size.");
        }
        this.distanceMatrix = distanceMatrix;
        this.initialSolution = initialSolution;
        tour = initialSolution.getTour();
    }

    @Override
    public Solution getSolution() {
        int bestDistance = getDistance(tour);
        boolean wasImproved = true;
        while (wasImproved) {
            final int newDistance = tryImproveDistance(bestDistance);
            wasImproved = newDistance < bestDistance;
            if (wasImproved) {
                bestDistance = newDistance;
            }
        }

        return new Solution(tour, bestDistance);
    }

    private int tryImproveDistance(int bestDistance) {
        int newDistance = 0;
        outer : for (int from = 1; from < tour.size() - 1; from++) {
            for (int to = from + 1; to < tour.size(); to++) {
                final List<Integer> newTour = twOptSwap(tour, from, to);
                newDistance = getDistance(newTour);
                if (newDistance < bestDistance) {
                    tour = newTour;
                    break outer;
                }
            }
        }
        return newDistance;
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
        return getDistance(tour, 0, tour.size() - 1);
    }

    private int getDistance(List<Integer> tour, int from, int to) {
        int distance = 0;
        for (int i = from; i < to; i++) {
            distance += distanceMatrix[tour.get(i)][tour.get(i + 1)];
        }
        return distance;
    }
}
