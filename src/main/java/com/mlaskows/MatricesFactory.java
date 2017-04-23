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
    private int distanceMatrix[][];
    private int nearestNeighborList[][];
    //double heuristicInformationMatrix[][];

    public MatricesFactory(Item item, int nnFactor) {
        this.item = item;
        this.nnFactor = nnFactor;
    }

    public int[][] getDistanceMatrix() {
        if (distanceMatrix == null) {
            // TODO add some more logic to determine right function or
            // move it somewhere else or use polymorphism
            calculateDistances(DistanceCalculationMethodFactory
                    .getTwoNodesDistanceCalculationMethod(item.getEdgeWeightType()));
        }
        return distanceMatrix;
    }

    private void calculateDistances(
            BiFunction<Node, Node, Integer> twoNodesDistanceCalculationMethod) {
        distanceMatrix = new int[item.getDimension()][item.getDimension()];
        nearestNeighborList = new int[item.getDimension()][nnFactor];
        List<Node> nodes = item.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            List<Tuple> tuples = new ArrayList<>();
            for (int j = 0; j < nodes.size(); j++) {
                int distance;
                if (i == j) {
                    // TODO check if it's valid! Maybe should be 0?
                    distance = Integer.MAX_VALUE;

                } else {
                    distance = twoNodesDistanceCalculationMethod
                            .apply(nodes.get(i), nodes.get(j));
                }
                distanceMatrix[i][j] = distance;
                tuples.add(new Tuple(j, distance));
            }
            // TODO consider performance improvement
            nearestNeighborList[i] = tuples.stream()
                    .sorted()
                    .limit(nnFactor)
                    .mapToInt(t -> t.getIndex())
                    .toArray();
        }
    }

    public int[][] getNearestNeighborList() {
        if (nearestNeighborList == null) {
            getDistanceMatrix();
        }
        return nearestNeighborList;
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
