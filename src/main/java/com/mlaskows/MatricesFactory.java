package com.mlaskows;

import com.mlaskows.tsplib.DistanceCalculationMethodFactory;
import com.mlaskows.tsplib.Item;
import com.mlaskows.tsplib.Node;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by mlaskows on 20/04/2017.
 */
public class MatricesFactory {
    private final Item item;
    private int distanceMatrix[][];
    private int nearestNeighborList[][];
    //double heuristicInformationMatrix[][];

    public MatricesFactory(Item item) {
        this.item = item;
    }

    public int[][] getDistanceMatrix() {
        if (distanceMatrix == null) {
            // TODO add some more logic to determin right function or
            // move it somewhere else or use polymorphism
            calculateDistances(DistanceCalculationMethodFactory
                    .getTwoNodesDistanceCalculationMethod(item.getEdgeWeightType()));
        }
        return distanceMatrix;
    }

    private void calculateDistances(
            BiFunction<Node, Node, Integer> twoNodesDistanceCalculationMethod) {
        distanceMatrix = new int[item.getDimension()][item.getDimension()];
        List<Node> nodes = item.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) {
                    // TODO check if it's valid! Maybe should be 0?
                    distanceMatrix[i][j] = Integer.MAX_VALUE;
                    continue;
                }
                distanceMatrix[i][j] = twoNodesDistanceCalculationMethod
                        .apply(nodes.get(i), nodes.get(j));
            }
        }
    }


    public int[][] getNearestNeighborList() {
        return nearestNeighborList;
    }
}
