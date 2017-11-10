/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mlaskows.antsp.datamodel.data;

import com.mlaskows.antsp.datamodel.Solution;

import java.util.Optional;

/**
 * Contains data needed to calculate optimal route. Values of those
 * data shouldn't be changed during run.
 *
 * @author Maciej Laskowski
 */
public class StaticData {

    private final int distanceMatrix[][];
    private final Optional<int[][]> nearestNeighborsMatrix;
    private final Optional<double[][]> heuristicInformationMatrix;
    private final Optional<Solution> heuristicSolution;

    public StaticData(int[][] distanceMatrix, int[][] nearestNeighborsMatrix, double[][] heuristicInformationMatrix,
                      Solution heuristicSolution) {
        this.distanceMatrix = distanceMatrix;
        this.nearestNeighborsMatrix = Optional.ofNullable(nearestNeighborsMatrix);
        this.heuristicInformationMatrix = Optional.ofNullable(heuristicInformationMatrix);
        this.heuristicSolution = Optional.ofNullable(heuristicSolution);
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    /**
     * Returns matrix with nearest neighbor lists of depth NN_FACTOR
     * In addition to the distance matrix, it is convenient to store for each
     * city a list of its nearest neighbors.
     * <p>
     * The position r of a city j in city i’s nearest-neighbor list
     * nearestNeighbors[i]  is the index of the distance dij in the sorted list di',
     * that is, nearestNeighbors[i][r]  gives the identifier (index) of the r-th
     * nearest city to city i (i.e., nearestNeighbors[i][r]  = j)
     *
     * @return nearest neighbour matrix if present
     */
    public Optional<int[][]> getNearestNeighborsMatrix() {
        return nearestNeighborsMatrix;
    }

    /**
     * Returns heuristic information matrix.
     * <p>
     * Can be used to compute choiceInfo value. Since heuristic information
     * never changes this can be done once and reused.
     * <p>
     * The heuristic information nij is typically inversely proportional to
     * the distance between cities i and j, a straightforward choice being
     * nij = 1/dij
     *
     * @return heuristic information matrix if present
     */
    public Optional<double[][]> getHeuristicInformationMatrix() {
        return heuristicInformationMatrix;
    }

    /**
     * Returns size of the problem graph which is distance matrix length.
     *
     * @return size of the problem graph
     */
    public int getProblemSize() {
        return distanceMatrix.length;
    }

    public Optional<Solution> getHeuristicSolution() {
        return heuristicSolution;
    }
}
