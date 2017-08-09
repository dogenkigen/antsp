package com.mlaskows.antsp.solvers.heuristic;

import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;
import com.mlaskows.antsp.solvers.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

public class NewTwoOptSolver implements Solver {

    private final Solution initialSolution;
    private final int[][] distanceMatrix;
    private final int[][] nearestNeighboursMatrix;
    private final int problemSize;

    public NewTwoOptSolver(Solution initialSolution, StaticMatrices matrices) {
        this.initialSolution = initialSolution;
        problemSize = matrices.getProblemSize();
        distanceMatrix = matrices.getDistanceMatrix();
        nearestNeighboursMatrix = matrices.getNearestNeighborsMatrix().get();
    }

    @Override
    public Solution getSolution() {
        final List<Integer> tour = initialSolution
                .getTour();
        tour.add(tour.get(0));
        final int[] initialTour = tour.stream().mapToInt(i -> i).toArray();
        final List<Integer> ts = Arrays.stream(twoOptFirst(initialTour))
                .boxed()
                .collect(toList());
        ts.remove(ts.size() - 1);
        return new Solution(ts, getTourLenght
                (initialTour));
    }

    private int getTourLenght(int[] tour) {
        int distance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            distance += distanceMatrix[tour[i]][tour[i + 1]];
        }
        return distance;
    }


    /*
     * FUNCTION: 2-opt a tour
     * INPUT: pointer to the tour that undergoes local optimization
     * OUTPUT: none
     * (SIDE)EFFECTS: tour is 2-opt
     * COMMENTS: the neighbouAnts.rhood is scanned in random order (this need
     * not be the best possible choice). Concerning the speed-ups used
     * here consult, for example, Chapter 8 of
     * Holger H. Hoos and Thomas Stuetzle,
     * Stochastic Local Search---Foundations and Applications,
     * Morgan Kaufmann Publishers, 2004.
     * or some of the papers online available from David S. Johnson.
     */
    private int[] twoOptFirst(int[] tour) {
        boolean gotoExchange = false;

        int c1, c2; /* cities considered for an exchange */
        int successorC1, successorC2; /* successor cities of c1 and c2 */
        int predecessorC1, predecessorC2; /* predecessor cities of c1 and c2 */
        int positionC1, positionC2; /* positions of cities c1, c2 */
        int i, j, h, l;
        int help;
        boolean wasImproved;
        int h1 = 0, h2 = 0, h3 = 0, h4 = 0;
        int radius; /* radius of nn-search */
        int gain;
        int[] randomVector;
        int[] positionOfCitiesInTour = new int[problemSize];
        boolean[] dontLookBits = new boolean[problemSize];
        for (i = 0; i < problemSize; i++) {
            positionOfCitiesInTour[tour[i]] = i;
            dontLookBits[i] = false;
        }

        wasImproved = true;
        randomVector = generateRandomPermutation(problemSize);

        while (wasImproved) {

            wasImproved = false;

            for (l = 0; l < problemSize; l++) {

                c1 = randomVector[l];
                if (dontLookBits[c1])
                    continue;
                positionC1 = positionOfCitiesInTour[c1];
                successorC1 = tour[positionC1 + 1];
                radius = distanceMatrix[c1][successorC1];

		/* First search for c1's nearest neighbours, use successor of c1 */
                int nn_ls = nearestNeighboursMatrix[0].length;
                for (h = 0; h < nn_ls; h++) {
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
                            gotoExchange = true;
                            break;
                        }
                    } else
                        break;
                }

                if (gotoExchange) {
            /* Search one for next c1's h-nearest neighbours, use predecessor c1 */
                    if (positionC1 > 0)
                        predecessorC1 = tour[positionC1 - 1];
                    else
                        predecessorC1 = tour[problemSize - 1];
                    radius = distanceMatrix[predecessorC1][c1];
                    for (h = 0; h < nn_ls; h++) {
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
                                gotoExchange = true;
                                break;
                            }
                        } else
                            break;
                    }
                }

                if (!gotoExchange) {
            /* No exchange */
                    dontLookBits[c1] = true;
                    continue;
                }

                gotoExchange = false;
                wasImproved = true;
                dontLookBits[h1] = false;
                dontLookBits[h2] = false;
                dontLookBits[h3] = false;
                dontLookBits[h4] = false;
            /* Now perform move */
                if (positionOfCitiesInTour[h3] < positionOfCitiesInTour[h1]) {
                    help = h1;
                    h1 = h3;
                    h3 = help;
                    help = h2;
                    h2 = h4;
                    h4 = help;
                }
                if (positionOfCitiesInTour[h3] - positionOfCitiesInTour[h2] < problemSize / 2 + 1) {
        /* reverse inner part from pos[h2] to pos[h3] */
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
                } else {
        /* reverse outer part from pos[h4] to pos[h1] */
                    i = positionOfCitiesInTour[h1];
                    j = positionOfCitiesInTour[h4];
                    if (j > i)
                        help = problemSize - (j - i) + 1;
                    else
                        help = (i - j) + 1;
                    help = help / 2;
                    for (h = 0; h < help; h++) {
                        c1 = tour[i];
                        c2 = tour[j];
                        tour[i] = c2;
                        tour[j] = c1;
                        positionOfCitiesInTour[c1] = j;
                        positionOfCitiesInTour[c2] = i;
                        i--;
                        j++;
                        if (i < 0)
                            i = problemSize - 1;
                        if (j >= problemSize)
                            j = 0;
                    }
                    tour[problemSize] = tour[0];
                }

            }
        }
        return tour;

    }

    /*
     * FUNCTION: generate a random permutation of the integers 0 .. n-1
     * INPUT: length of the array
     * OUTPUT: pointer to the random permutation
     * (SIDE)EFFECTS: the array holding the random permutation is allocated in this
     * function. Don't forget to free again the memory!
     * COMMENTS: only needed by the local search procedures
     */
    private int[] generateRandomPermutation(int n) {
        int tmp, node, totalAssigned = 0;
        double random;
        int[] result;

        result = new int[n];

        for (int i = 0; i < n; i++)
            result[i] = i;

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

}
