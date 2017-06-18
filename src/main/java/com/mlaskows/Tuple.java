package com.mlaskows;

//TODO consider keeping data in tuples. This might be memory overhead but can
// reduce computation time
public class Tuple implements Comparable<Tuple> {

    private final int index;
    private final int distance;

    public Tuple(int index, int distance) {
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

