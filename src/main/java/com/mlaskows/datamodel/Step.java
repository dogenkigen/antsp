package com.mlaskows.datamodel;

public class Step {

    private final int from;
    private final int to;
    private final int distance;

    public Step(int from, int to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

}

