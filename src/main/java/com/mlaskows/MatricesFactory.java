package com.mlaskows;

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
    private final double initialTrail;
    private int distanceMatrix[][];
    private int nearestNeighborList[][];
    private double pheromoneMatrix[][];
    /**
     * The heuristic information nij is typically inversely proportional to
     * the distance between cities i and j, a straightforward choice being
     * nij = 1/dij
     */
    private double heuristicInformationMatrix[][];

    public MatricesFactory(Item item, int nnFactor, double initialTrail) {
        this.item = item;
        this.nnFactor = nnFactor;
        this.initialTrail = initialTrail;
    }

    // FIXME this getters are shit I guess
    public int[][] getDistanceMatrix() {
        if (distanceMatrix == null) {
            calculateMatrices();
        }
        return distanceMatrix;
    }

    public int[][] getNearestNeighborList() {
        if (nearestNeighborList == null) {
            calculateMatrices();
        }
        return nearestNeighborList;
    }

    public double[][] getHeuristicInformationMatrix() {
        if (heuristicInformationMatrix == null) {
            calculateMatrices();
        }
        return heuristicInformationMatrix;
    }

    private void calculateMatrices() {
        // TODO add some more logic to determine right function or
        // move it somewhere else or use polymorphism
        calculateMatrices(DistanceCalculationMethodFactory
                .getTwoNodesDistanceCalculationMethod(item.getEdgeWeightType()));
    }

    private void calculateMatrices(
            BiFunction<Node, Node, Integer> twoNodesDistanceCalculationMethod) {
        initArrays();
        List<Node> nodes = item.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            List<Tuple> tuples = new ArrayList<>();
            // TODO consider reducing number of iterations
            // It can be started like j = i if assignment will be done also
            // for inverted pairs
            // But think twice here since this inner loop may be used to
            // performance improvement in NN calculations
            for (int j = 0; j < nodes.size(); j++) {
                int distance;
                // TODO consider not calculating for already calculated
                //boolean redundant = distanceMatrix[j][i] != 0;
                if (i == j) {
                    // TODO check if it's valid! Maybe should be 0?
                    distance = Integer.MAX_VALUE;

                } else {
                    distance = twoNodesDistanceCalculationMethod
                            .apply(nodes.get(i), nodes.get(j));
                }
                distanceMatrix[i][j] = distance;
                heuristicInformationMatrix[i][j] = (1.0 / ((double) distance + 0.1));
                tuples.add(new Tuple(j, distance));
            }
            nearestNeighborList[i] = getNearestNeighbourRow(tuples);
        }
    }

    private void initArrays() {
        distanceMatrix = new int[item.getDimension()][item.getDimension()];
        heuristicInformationMatrix = new double[item.getDimension()][item
                .getDimension()];
        nearestNeighborList = new int[item.getDimension()][nnFactor];
    }

    private int[] getNearestNeighbourRow(List<Tuple> tuples) {
        // TODO consider performance improvement
        return tuples.stream()
                .sorted()
                .limit(nnFactor)
                .mapToInt(t -> t.getIndex())
                .toArray();
    }

    private class Tuple implements Comparable<Tuple> {
        private final int index;
        private final int distance;

        private Tuple(int index, int distance) {
            this.index = index;
            this.distance = distance;
        }

        public int getIndex() {
            return index;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(Tuple tuple) {
            return distance - tuple.getDistance();
        }
    }

}
