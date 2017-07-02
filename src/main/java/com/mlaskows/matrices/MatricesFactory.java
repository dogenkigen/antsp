package com.mlaskows.matrices;

import com.mlaskows.datamodel.Step;
import com.mlaskows.solvers.NearestNeighbourSolver;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.tsplib.DistanceCalculationMethodFactory;
import com.mlaskows.tsplib.Item;
import com.mlaskows.tsplib.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by mlaskows on 20/04/2017.
 */
public class MatricesFactory {
    private final Item item;
    private final int nnFactor;
    private int distanceMatrix[][];
    private int nearestNeighbors[][];
    private double pheromoneMatrix[][];
    /**
     * The heuristic information nij is typically inversely proportional to
     * the distance between cities i and j, a straightforward choice being
     * nij = 1/dij
     */
    private double heuristicInformationMatrix[][];

    public MatricesFactory(Item item, int nnFactor) {
        this.item = item;
        this.nnFactor = nnFactor;
    }

    public MatricesHolder createMatrices() {
        calculateMatrices();
        return new MatricesHolder.MatricesHolderBuilder().withDistanceMatrix
                (distanceMatrix).withNearestNeighbors(nearestNeighbors)
                .withHeuristicInformationMatrix(heuristicInformationMatrix)
                .withPheromoneMatrix(pheromoneMatrix).build();
    }

    private void calculateMatrices() {
        final BiFunction<Node, Node, Integer> distanceCalculationMethod =
                DistanceCalculationMethodFactory
                        .getDistanceCalculationMethod(item.getEdgeWeightType());
        calculateMatrices(distanceCalculationMethod);
    }

    private void calculateMatrices(
            BiFunction<Node, Node, Integer> distanceCalculationMethod) {
        if (isAtLeastOneArrayEmpty()) {
            calculateBasicMatrices(distanceCalculationMethod);
        }
        final NearestNeighbourSolver nearestNeighbourSolver = new NearestNeighbourSolver(distanceMatrix);
        final Solution solution = nearestNeighbourSolver.getSolution();
        final double initialPheromoneValue = (double) distanceMatrix.length /
                solution.getTourLength();
        final int size = item.getNodes().size();
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                fill(pheromoneMatrix, i, j, initialPheromoneValue);
            }
        }
    }

    private boolean isAtLeastOneArrayEmpty() {
        return distanceMatrix == null || heuristicInformationMatrix == null ||
                pheromoneMatrix == null || nearestNeighbors == null;
    }

    private void calculateBasicMatrices(BiFunction<Node, Node, Integer> distanceCalculationMethod) {
        initArrays();
        List<Node> nodes = item.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            List<Step> steps = new ArrayList<>(nodes.size());
            for (int j = i; j < nodes.size(); j++) {
                int distance = getDistance(nodes
                        .get(i), nodes.get(j), distanceCalculationMethod);
                fill(distanceMatrix, i, j, distance);
                fill(heuristicInformationMatrix, i, j, (1.0 / ((double) distance + 0.1)));
                steps.add(new Step(j, distance));
            }
            nearestNeighbors[i] = getNearestNeighbourRow(steps);
        }
    }

    private void fill(int[][] matrix, int i, int j, int value) {
        matrix[i][j] = value;
        matrix[j][i] = value;
    }

    private void fill(double[][] matrix, int i, int j, double value) {
        matrix[i][j] = value;
        matrix[j][i] = value;
    }

    private int getDistance(Node nodeI, Node nodeJ,
                            BiFunction<Node, Node, Integer>
                                    distanceCalculationMethod) {
        int distance;
        if (nodeI.equals(nodeJ)) {
            distance = Integer.MAX_VALUE;

        } else {
            distance = distanceCalculationMethod
                    .apply(nodeI, nodeJ);
        }
        return distance;
    }

    private void initArrays() {
        distanceMatrix = new int[item.getDimension()][item.getDimension()];
        heuristicInformationMatrix = new double[item.getDimension()][item
                .getDimension()];
        pheromoneMatrix = new double[item.getDimension()][item.getDimension()];
        nearestNeighbors = new int[item.getDimension()][nnFactor];
    }

    private int[] getNearestNeighbourRow(List<Step> steps) {
        // TODO consider performance improvement
        return steps.stream()
                .sorted()
                .limit(nnFactor)
                .mapToInt(t -> t.getTo())
                .toArray();
    }

}
