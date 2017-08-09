package com.mlaskows.datamodel;

public class Step implements Comparable<Step> {

    private final int to;
    private final int distance;

    public Step(int to, int distance) {
        this.to = to;
        this.distance = distance;
    }

    public int getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Step step) {
        return distance - step.getDistance();
    }

}

