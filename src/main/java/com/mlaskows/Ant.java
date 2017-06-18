package com.mlaskows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlaskows on 17/06/2017.
 */
public class Ant {

    private int tourLength;
    private final List<Integer> tour;
    private final boolean visited[];

    public Ant(int problemSize) {
        tour = new ArrayList<>();
        visited = new boolean[problemSize];
    }


    public void visit(int index, int stepLength) {
        tourLength = tourLength + stepLength;
        visited[index] = true;
        tour.add(index);
    }

    public boolean isVisited(int index) {
        return visited[index];
    }


    public int getTourLength() {
        return tourLength;
    }

    /**
     * ant’s memory storing (partial) tours.
     * <p>
     * For the TSP we represent tours by arrays of length numberOfCites + 1, where at
     * position numberOfCites + 1 the first city is repeated. This choice makes
     * easier some of the other procedures like the computation of the tour
     * length.
     * FIXME check if in this implementation it will be actually true because
     * for now it's not
     */
    public List<Integer> getTour() {
        return tour;
    }

    /**
     * An additional array visited whose values are set to visited[i]
     * true if city i has already been visited by the ant, and to visited[i]
     * false otherwise. This array is updated by the ant while it builds
     * a solution.
     */
    public boolean[] getVisited() {
        return visited;
    }
}
