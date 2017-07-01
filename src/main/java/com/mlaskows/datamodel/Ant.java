package com.mlaskows.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlaskows on 17/06/2017.
 */
// TODO make it thread safe
public class Ant {

    private int tourLength;
    private final List<Integer> tour;
    private final boolean visited[];

    public Ant(int problemSize, int initialPosition) {
        tour = new ArrayList<>(problemSize);
        visited = new boolean[problemSize];
        visit(initialPosition, 0);
    }

    public void visit(int index, int stepLength) {
        tourLength = tourLength + stepLength;
        visited[index] = true;
        tour.add(index);
    }

    public boolean isVisited(int index) {
        return visited[index];
    }

    public boolean notVisited(int index) {
        return !visited[index];
    }

    public int getTourLength() {
        return tourLength;
    }

    public int getCurrent() {
        return tour.get(tour.size() - 1);
    }

    /**
     * ant’s memory storing (partial) tours.
     */
    public List<Integer> getTour() {
        return tour;
    }

}
