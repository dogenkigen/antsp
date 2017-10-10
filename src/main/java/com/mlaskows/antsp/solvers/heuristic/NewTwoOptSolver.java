package com.mlaskows.antsp.solvers.heuristic;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NewTwoOptSolver implements Solver {

    private final Solution initialSolution;
    private final int[][] distanceMatrix;
    private final int[][] nearestNeighboursMatrix;
    private final int problemSize;
    private boolean shouldStop;

    public NewTwoOptSolver(Solution initialSolution, StaticMatrices matrices) {
        this.initialSolution = initialSolution;
        problemSize = matrices.getProblemSize();
        distanceMatrix = matrices.getDistanceMatrix();
        nearestNeighboursMatrix = matrices.getNearestNeighborsMatrix()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Solution getSolution() {
        final List<Integer> ts = getSolutionWithTypeConversion();
        return new Solution(ts, getDistance(ts));
    }

    private List<Integer> getSolutionWithTypeConversion() {
        final List<Integer> tour = initialSolution
                .getTour();
        final int[] initialTour = tour.stream()
                .mapToInt(i -> i)
                .toArray();
        final List<Integer> ts = Arrays.stream(twoOpt(initialTour))
                .boxed()
                .collect(toList());
        return ts;
    }

    /**
     * 2-opt algorithm implementation.
     *
     * @param tour array to be optimized
     * @return optimized tour
     */
    private int[] twoOpt(int[] tour) {
        boolean exchange = false;

        int c1, c2; /* cities considered for an exchange */
        int successorC1, successorC2; /* successor cities of c1 and c2 */
        int predecessorC1, predecessorC2; /* predecessor cities of c1 and c2 */
        int positionC1, positionC2; /* positions of cities c1, c2 */
        boolean wasImproved;
        int h1 = 0, h2 = 0, h3 = 0, h4 = 0;
        int radius; /* radius of nn-search */
        int gain;
        int[] randomVector;
        int[] positionOfCitiesInTour = new int[problemSize];
        boolean[] dontLookBits = new boolean[problemSize];
        for (int i = 0; i < problemSize; i++) {
            positionOfCitiesInTour[tour[i]] = i;
            dontLookBits[i] = false;
        }

        wasImproved = true;
        randomVector = generateRandomPermutation(problemSize);

        while (wasImproved && !shouldStop) {

            wasImproved = false;

            for (int l = 0; l < problemSize; l++) {

                c1 = randomVector[l];
                if (dontLookBits[c1]) {
                    continue;
                }
                positionC1 = positionOfCitiesInTour[c1];
                successorC1 = tour[positionC1 + 1];
                radius = distanceMatrix[c1][successorC1];

		/* First search for c1's nearest neighbours, use successor of c1 */
                int nnCount = nearestNeighboursMatrix[0].length;
                for (int h = 0; h < nnCount; h++) {
                    c2 = nearestNeighboursMatrix[c1][h]; /* exchange partner, determine its position */
                    if (radius > distanceMatrix[c1][c2]) {
                        successorC2 = tour[positionOfCitiesInTour[c2] + 1];
                        gain = -radius + distanceMatrix[c1][c2] + distanceMatrix[successorC1][successorC2]
                                - distanceMatrix[c2][successorC2];
                        if (gain < 0) {
                            h1 = c1;
                            h2 = successorC1;
                            h3 = c2;
                            h4 = successorC2;
                            exchange = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }

                if (exchange) {
            /* Search one for next c1's h-nearest neighbours, use predecessor c1 */
                    if (positionC1 > 0) {
                        predecessorC1 = tour[positionC1 - 1];
                    } else {
                        predecessorC1 = tour[problemSize - 1];
                    }
                    radius = distanceMatrix[predecessorC1][c1];
                    for (int h = 0; h < nnCount; h++) {
                        c2 = nearestNeighboursMatrix[c1][h]; /* exchange partner, determine its position */
                        if (radius > distanceMatrix[c1][c2]) {
                            positionC2 = positionOfCitiesInTour[c2];
                            if (positionC2 > 0)
                                predecessorC2 = tour[positionC2 - 1];
                            else
                                predecessorC2 = tour[problemSize - 1];
                            if (predecessorC2 == c1)
                                continue;
                            if (predecessorC1 == c2)
                                continue;
                            gain = -radius + distanceMatrix[c1][c2] + distanceMatrix[predecessorC1][predecessorC2]
                                    - distanceMatrix[predecessorC2][c2];
                            if (gain < 0) {
                                h1 = predecessorC1;
                                h2 = c1;
                                h3 = predecessorC2;
                                h4 = c2;
                                exchange = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                /* No exchange */
                if (!exchange) {
                    dontLookBits[c1] = true;
                    continue;
                }

                exchange = false;
                wasImproved = true;
                fillDontLookBits(h1, h2, h3, h4, dontLookBits);
                /* Now perform move */
                twoOptSwap(positionOfCitiesInTour, h1, h2, h3, h4, tour);

            }
        }
        return tour;
    }

    private void twoOptSwap(int[] positionOfCitiesInTour, int h1, int h2, int h3,
                            int h4, int[] tour) {
        int tmp;
        int i;
        int j;
        int c1;
        int c2;
        if (positionOfCitiesInTour[h3] < positionOfCitiesInTour[h1]) {
            tmp = h1;
            h1 = h3;
            h3 = tmp;
            tmp = h2;
            h2 = h4;
            h4 = tmp;
        }
        /* reverse inner part from pos[h2] to pos[h3] */
        if (positionOfCitiesInTour[h3] - positionOfCitiesInTour[h2] < problemSize / 2 + 1) {
            i = positionOfCitiesInTour[h2];
            j = positionOfCitiesInTour[h3];
            while (i < j) {
                c1 = tour[i];
                c2 = tour[j];
                tour[i] = c2;
                tour[j] = c1;
                positionOfCitiesInTour[c1] = j;
                positionOfCitiesInTour[c2] = i;
                i++;
                j--;
            }
        /* reverse outer part from pos[h4] to pos[h1] */
        } else {
            i = positionOfCitiesInTour[h1];
            j = positionOfCitiesInTour[h4];
            if (j > i) {
                tmp = problemSize - (j - i) + 1;
            } else {
                tmp = (i - j) + 1;
            }
            tmp = tmp / 2;
            for (int h = 0; h < tmp; h++) {
                c1 = tour[i];
                c2 = tour[j];
                tour[i] = c2;
                tour[j] = c1;
                positionOfCitiesInTour[c1] = j;
                positionOfCitiesInTour[c2] = i;
                i--;
                j++;
                if (i < 0) {
                    i = problemSize - 1;
                }
                if (j >= problemSize) {
                    j = 0;
                }
            }
            tour[problemSize] = tour[0];
        }
    }

    private void fillDontLookBits(int h1, int h2, int h3, int h4, boolean[] dontLookBits) {
        dontLookBits[h1] = false;
        dontLookBits[h2] = false;
        dontLookBits[h3] = false;
        dontLookBits[h4] = false;
    }

    /*
     * Generate a random permutation of the integers 0 .. n-1
     */
    private int[] generateRandomPermutation(int n) {
        int tmp, node, totalAssigned = 0;
        double random;
        int[] result;

        result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = i;
        }

        for (int i = 0; i < n; i++) {
        /* find (randomly) an index for a free unit */
            random = ThreadLocalRandom.current().nextDouble(0, 1);
            node = (int) (random * (n - totalAssigned));
            assert (i + node < n);
            tmp = result[i];
            result[i] = result[i + node];
            result[i + node] = tmp;
            totalAssigned++;
        }
        return result;
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
