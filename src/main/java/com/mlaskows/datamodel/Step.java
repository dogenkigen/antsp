package com.mlaskows.datamodel;

//TODO consider keeping data in steps. This might be memory overhead but can
// reduce computation time
public class Step implements Comparable<Step> {

    private final int index;
    private final int distance;

    public Step(int index, int distance) {
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
    public int compareTo(Step step) {
        return distance - step.getDistance();
    }
}

