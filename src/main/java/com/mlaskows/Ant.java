package com.mlaskows;

/**
 * Created by mlaskows on 17/06/2017.
 */
public class Ant {
    public int tourLength; // the ant’s tour length

    /**
     * ant’s memory storing (partial) tours.
     * <p>
     * For the TSP we represent tours by arrays of length numberOfCites + 1, where at
     * position numberOfCites + 1 the first city is repeated. This choice makes
     * easier some of the other procedures like the computation of the tour
     * length.
     */
    public int tour[];

    /**
     * An additional array visited whose values are set to visited[j]
     * true if city i has already been visited by the ant, and to visited[j]
     * false otherwise. This array is updated by the ant while it builds
     * a solution.
     */
    public boolean visited[]; // visited cities
}
