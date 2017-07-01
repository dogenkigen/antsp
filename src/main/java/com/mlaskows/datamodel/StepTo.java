package com.mlaskows.datamodel;

//TODO consider keeping data in steps. This might be memory overhead but can
// reduce computation time
public class StepTo implements Comparable<StepTo> {

    private final int to;
    private final int distance;

    public StepTo(int to, int distance) {
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
    public int compareTo(StepTo step) {
        return distance - step.getDistance();
    }

}

