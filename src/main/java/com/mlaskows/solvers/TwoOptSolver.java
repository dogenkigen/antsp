package com.mlaskows.solvers;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.matrices.StaticMatricesHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolver implements Solver {

    private final int[][] distanceMatrix;
    private final int[][] nearestNeighboursMatrix;
    private List<Integer> tour;

    public TwoOptSolver(Solution initialSolution, StaticMatricesHolder matricesHolder) {
        this.distanceMatrix = matricesHolder.getDistanceMatrix();
        if (initialSolution.getTour().size() != distanceMatrix.length) {
            throw new IllegalArgumentException("Initial solution tour size " + initialSolution.getTour().size() +
                    " should be equal to problem size " + distanceMatrix.length);
        }
        nearestNeighboursMatrix = matricesHolder.getNearestNeighborsMatrix()
                .orElseThrow(() -> new IllegalArgumentException("Nearest neighbors matrix can't be empty!"));
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
        // from value is inclusive and to value is exclusive
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

    private boolean isWorthToImprove(List<Integer> tour, int from, int to) {
        int c1 = tour.get(from - 1);
        int c2 = tour.get(from);
        int c3 = tour.get(to);
        int c4 = to + 1 == tour.size() ? -1 : tour.get(to + 1);
//isInNearestNeighbourhood(c1, c2, c3, c4) &&
        return isShorterDistance(c1, c2, c3, c4);
    }

    private boolean isInNearestNeighbourhood(int c1, int c2, int c3, int c4) {
        for (int i = 0; i < this.nearestNeighboursMatrix[0].length; i++) {
            if (this.nearestNeighboursMatrix[c2][i] == c1
                    && (c4 < 0 || this.nearestNeighboursMatrix[c3][i] == c4)) {
                return true;
            }
        }
        return false;
    }

    public boolean isShorterDistance(int c1, int c2, int c3, int c4) {
        return c4 > 0 ?
                distanceMatrix[c1][c2] + distanceMatrix[c3][c4] > distanceMatrix[c1][c3] + distanceMatrix[c2][c4] :
                distanceMatrix[c1][c2] > distanceMatrix[c1][c3];
        //was c1c2> c1c3
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
