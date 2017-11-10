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

import com.mlaskows.BaseWithTspTest;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class StaticDataBuilderTest implements BaseWithTspTest {

    private static final int NN_FACOTR = 5;
    private static Tsp australiTsp;

    @BeforeClass
    public void init() throws IOException {
        australiTsp = getTsp("tsplib/australia.tsp");
    }

    @Test
    public void testDistancesAustralia() {
        StaticData data =
                new StaticDataBuilder(australiTsp).build();
        int[][] distanceMatrix = data.getDistanceMatrix();

        assertEquals(distanceMatrix.length, australiTsp.getDimension());
        assertEquals(distanceMatrix[3][3], Integer.MAX_VALUE);
        // Brisbane - Melbourne (real 1372.50)
        assertEquals(distanceMatrix[0][1], 1395);
        // Sydney - Darwin (real 3144)
        assertEquals(distanceMatrix[2][5], 3167);
    }

    @Test
    public void testNNAustralia() {
        StaticData data = new StaticDataBuilder(australiTsp)
                .withNearestNeighbors(NN_FACOTR)
                .build();
        int[][] nearestNeighborList = data
                .getNearestNeighborsMatrix()
                .orElseThrow(RuntimeException::new);
        assertEquals(nearestNeighborList.length, australiTsp.getDimension());
        assertEquals(nearestNeighborList[1].length, 5);
        assertEquals(nearestNeighborList[0][0], 2);
        assertEquals(nearestNeighborList[0][4], 3);
    }

    @Test
    public void testHeuristicAustralia() {
        StaticData data = new StaticDataBuilder(australiTsp)
                .withHeuristicInformationMatrix()
                .build();
        double[][] heuristicInformationMatrix = data
                .getHeuristicInformationMatrix()
                .orElseThrow(RuntimeException::new);
        assertEquals(heuristicInformationMatrix.length, australiTsp.getDimension());
        int i = data.getDistanceMatrix()[1][1];
        double v = 1.0 / ((double) i + 0.1);
        assertEquals(heuristicInformationMatrix[1][1], v);
    }

    @Test
    public void testBays29() throws IOException {
        final Tsp tsp = getTsp("tsplib/bays29.tsp");
        final StaticData data = new StaticDataBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(15)
                .build();

        assertTrue(tsp.getEdgeWeightData().isPresent());
        final int[][] distanceMatrix = data.getDistanceMatrix();
        final int[][] edgeWeightData = tsp.getEdgeWeightData().get();
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix.length; j++) {
                if (i != j) {
                    assertEquals(distanceMatrix[i][j], edgeWeightData[i][j]);
                } else {
                    assertEquals(distanceMatrix[i][j], Integer.MAX_VALUE);
                }
            }
        }
    }

    @Test
    public void testPa561Comparison() throws IOException {
        Tsp tsp = getTsp("tsplib/pa561.tsp");
        int[] tour = getTour("tsplib/pa561.opt.tour").getTour().get(0);
        StaticData data = new StaticDataBuilder(tsp).build();
        int[][] distanceMatrix = data.getDistanceMatrix();

        int tourLen = getTourLen(tour, distanceMatrix);

        assertEquals(tourLen, 2754);
    }

    @Test
    public void testGr202Comparison() throws IOException {
        Tsp tsp = getTsp("tsplib/gr202.tsp");
        int[] tour = getTour("tsplib/gr202.opt.tour").getTour().get(0);
        StaticData data = new StaticDataBuilder(tsp).build();
        int[][] distanceMatrix = data.getDistanceMatrix();

        int tourLen = getTourLen(tour, distanceMatrix);

        assertEquals(tourLen, 40215);
    }

    @Test
    public void testGr202HeuristicSolution() throws IOException {
        Tsp tsp = getTsp("tsplib/gr202.tsp");
        StaticData data = new StaticDataBuilder(tsp).withHeuristicSolution().build();
        Solution solution = data.getHeuristicSolution().orElseThrow(RuntimeException::new);

        assertEquals(solution.getTourLength(), 49719);
    }

    private int getTourLen(int[] tour, int[][] distanceMatrix) {
        int tourLen = 0;
        for (int i = 1; i < tour.length; i++) {
            tourLen += distanceMatrix[tour[i - 1] - 1][tour[i] - 1];
        }
        return tourLen;
    }

}
