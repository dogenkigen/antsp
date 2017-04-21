package com.mlaskows.tsplib;

import java.util.List;

/**
 * Created by mlaskows on 21/04/2017.
 */
public final class TSPLIBItem {

    private final String name;
    private final TSPLIBType type;
    private final EdgeWeightType edgeWeightType;
    private final int dimension;
    private final int capacity;
    private final String comment;
    private final List<Node> nodes;

    public TSPLIBItem(String name, TSPLIBType type, EdgeWeightType edgeWeightType,
                      int dimension, int capacity,
                      String comment, List<Node> nodes) {
        this.name = name;
        this.type = type;
        this.edgeWeightType = edgeWeightType;
        this.dimension = dimension;
        this.capacity = capacity;
        this.comment = comment;
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public TSPLIBType getType() {
        return type;
    }

    public EdgeWeightType getEdgeWeightType() {
        return edgeWeightType;
    }

    public int getDimension() {
        return dimension;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getComment() {
        return comment;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
