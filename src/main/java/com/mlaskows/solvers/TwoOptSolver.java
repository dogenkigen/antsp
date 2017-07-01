package com.mlaskows.solvers;

import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mlaskows on 01/07/2017.
 */
public class TwoOptSolver implements Solver {

    private final Solution initialSolution;
    private final int[][] distanceMatrix;
    private List<Step> steps;

    public TwoOptSolver(Solution initialSolution, int[][] distanceMatrix) {
        if (initialSolution.getTour().size() != distanceMatrix.length) {
            throw new IllegalArgumentException("Initial solution tour size " +
                    "should be equal to problem size.");
        }
        this.distanceMatrix = distanceMatrix;
        this.initialSolution = initialSolution;
        steps = getSteps(initialSolution.getTour());
    }

    @Override
    public Solution getSolution() {
        int bestDistance = getTotalDistance(steps);
        boolean canBeImproved = true;
        while (canBeImproved) {
            final int improvedDistance = tryImproveDistance(bestDistance);
            canBeImproved = improvedDistance < bestDistance;
        }

        return new Solution(getTour(steps), bestDistance);
    }

    private int tryImproveDistance(int bestDistance) {
        int distance = 0;
        for (int i = 0; i < steps.size() - 1; i++) {
            for (int k = i + 1; k < steps.size(); k++) {
                final List<Step> swappedSteps = twOptSwap(steps, i, k);
                distance = getTotalDistance(swappedSteps);
                if (distance < bestDistance) {
                    steps = swappedSteps;
                    break;
                }
            }
        }
        return distance;
    }

    private List<Step> twOptSwap(List<Step> steps, int i, int k) {
        final List<Step> swappedSteps = new ArrayList<>(steps.size());
        final int toIndex = i - 1 >= 1 ? i - 1 : 1;
        swappedSteps.addAll(steps.subList(1, toIndex));
        final List<Step> iToK = steps.subList(i, k);
        Collections.reverse(iToK);
        swappedSteps.addAll(iToK);
        swappedSteps.addAll(steps.subList(k + 1, steps.size() - 1));
        return swappedSteps;
    }

    private int getTotalDistance(List<Step> steps) {
        int ret = 0;
        for (Step step : steps) {
            ret += step.getDistance();
        }
        return ret;
    }

    private List<Step> getSteps(List<Integer> tour) {
        final List<Step> steps = new ArrayList<>(tour.size());
        for (int i = 0; i < tour.size() - 1; i++) {
            steps.add(new Step(i, tour.get(i + 1),
                    distanceMatrix[tour.get(i)][tour.get(i + 1)]));
        }
        return steps;
    }

    private List<Integer> getTour(List<Step> steps) {
        List<Integer> tour = new ArrayList<>(initialSolution.getTour().size());
        for (int i = 0; i < steps.size(); i++) {
            tour.add(steps.get(i).getFrom());
            if (i == steps.size() - 1) {
                tour.add(steps.get(i).getTo());
            }
        }
        return tour;
    }
}
