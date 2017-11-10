/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mlaskows.antsp.solvers.heuristic;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.Solver;

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
    private boolean shouldStop;

    public TwoOptSolver(Solution initialSolution, StaticData dataHolder) {
        this.distanceMatrix = dataHolder.getDistanceMatrix();
        nearestNeighboursMatrix = dataHolder.getNearestNeighborsMatrix()
                .orElse(null);
        tour = initialSolution.getTour();
    }

    @Override
    public Solution getSolution() {
        boolean wasImproved = true;
        while (wasImproved && !shouldStop) {
            wasImproved = tryToImproveDistance();
        }
        return new Solution(tour, getDistance(tour));
    }

    private boolean tryToImproveDistance() {
        // From value is inclusive and to value is exclusive
        for (int from = 1; from < tour.size() - 1; from++) {
            for (int to = from + 1; to < tour.size(); to++) {
                if (isWorthToImprove(tour, from, to)) {
                    // TODO for now it seeks for the fist better than
                    // existing solution. Consider seeking for the best
                    // solution.
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
        // End of the tour case
        int c4 = to + 1 == tour.size() ? -1 : tour.get(to + 1);
        if (nearestNeighboursMatrix != null) {
            return isInNearestNeighbourhood(c1, c2, c3, c4)
                    && isShorterDistance(c1, c2, c3, c4);
        }
        return isShorterDistance(c1, c2, c3, c4);
    }

    private boolean isInNearestNeighbourhood(int c1, int c2, int c3, int c4) {
        // TODO Consider parameterizing OR instead of AND
        return isFromInNearestNeighbourhood(c1, c2)
                && isToInNearestNeighbourhood(c3, c4);
    }

    private boolean isFromInNearestNeighbourhood(int c1, int c2) {
        for (int i = 0; i < this.nearestNeighboursMatrix[c2].length; i++) {
            if (this.nearestNeighboursMatrix[c2][i] == c1) {
                return true;
            }
        }
        return false;
    }

    private boolean isToInNearestNeighbourhood(int c3, int c4) {
        if (c4 < 0) {
            return true;
        }
        for (int i = 0; i < this.nearestNeighboursMatrix[c3].length; i++) {
            if (this.nearestNeighboursMatrix[c3][i] == c4) {
                return true;
            }
        }
        return false;
    }

    public boolean isShorterDistance(int c1, int c2, int c3, int c4) {
        return c4 >= 0 ?
                distanceMatrix[c1][c2] + distanceMatrix[c3][c4]
                        > distanceMatrix[c1][c3] + distanceMatrix[c2][c4] :
                distanceMatrix[c1][c2] > distanceMatrix[c1][c3];
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

    @Override
    public void stop() {
        shouldStop = true;
    }

}
