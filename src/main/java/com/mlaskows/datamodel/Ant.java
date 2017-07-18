package com.mlaskows.datamodel;

import com.mlaskows.exeptions.SolutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlaskows on 17/06/2017.
 */
// TODO make it thread safe
public class Ant implements Comparable<Ant> {

    private int tourLength;
    private final List<Integer> tour;
    private final boolean visited[];

    public Ant(int problemSize, int initialPosition) {
        tour = new ArrayList<>(problemSize);
        visited = new boolean[problemSize];
        visit(initialPosition, 0);
    }

    public Ant(Solution solution) {
        tourLength = solution.getTourLength();
        tour = new ArrayList<>(solution.getTour());
        visited = new boolean[solution.getTour().size()];
        for (Integer index : tour) {
            visited[index] = true;
        }
    }

    public void visit(int index, int stepLength) {
        // FIXME remove this check for production version
        if (visited[index]) {
            throw new SolutionException("Following index already visited: " +
                    index);
        }
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

    public List<Integer> getTour() {
        return tour;
    }

    public Solution getSolution() {
        return new Solution(new ArrayList<>(tour), tourLength);
    }

    @Override
    public int compareTo(Ant o) {
        return this.getTourLength() - o.getTourLength();
    }
}
