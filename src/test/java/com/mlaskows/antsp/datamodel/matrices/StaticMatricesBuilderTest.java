package com.mlaskows.antsp.datamodel.matrices;

import com.mlaskows.BaseWithTspTest;
import com.mlaskows.tsplib.datamodel.item.Tour;
import com.mlaskows.tsplib.datamodel.item.Tsp;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class StaticMatricesBuilderTest implements BaseWithTspTest {

    private static final int NN_FACOTR = 5;
    private static Tsp australiTsp;

    @BeforeClass
    public void init() throws IOException {
        australiTsp = getTsp("tsplib/australia.tsp");
    }

    @Test
    public void testDistancesAustralia() {
        StaticMatrices matrices =
                new StaticMatricesBuilder(australiTsp).build();
        int[][] distanceMatrix = matrices.getDistanceMatrix();

        assertEquals(distanceMatrix.length, australiTsp.getDimension());
        assertEquals(distanceMatrix[3][3], Integer.MAX_VALUE);
        // Brisbane - Melbourne (real 1372.50)
        assertEquals(distanceMatrix[0][1], 1371);
        // Sydney - Darwin (real 3144)
        assertEquals(distanceMatrix[2][5], 2928);
    }

    @Test
    public void testNNAustralia() {
        StaticMatrices matrices = new StaticMatricesBuilder(australiTsp)
                .withNearestNeighbors(NN_FACOTR)
                .build();
        int[][] nearestNeighborList = matrices
                .getNearestNeighborsMatrix()
                .orElseThrow(RuntimeException::new);
        assertEquals(nearestNeighborList.length, australiTsp.getDimension());
        assertEquals(nearestNeighborList[1].length, 5);
        assertEquals(nearestNeighborList[0][0], 2);
        assertEquals(nearestNeighborList[0][4], 3);
    }

    @Test
    public void testHeuristicAustralia() {
        StaticMatrices matrices = new StaticMatricesBuilder(australiTsp)
                .withHeuristicInformationMatrix()
                .build();
        double[][] heuristicInformationMatrix = matrices
                .getHeuristicInformationMatrix()
                .orElseThrow(RuntimeException::new);
        assertEquals(heuristicInformationMatrix.length, australiTsp.getDimension());
        int i = matrices.getDistanceMatrix()[1][1];
        double v = 1.0 / ((double) i + 0.1);
        assertEquals(heuristicInformationMatrix[1][1], v);
    }

    @Test
    public void testBays29() throws IOException {
        final Tsp tsp = getTsp("tsplib/bays29.tsp");
        final StaticMatrices matrices = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .withNearestNeighbors(15)
                .build();

        assertTrue(tsp.getEdgeWeightData().isPresent());
        final int[][] distanceMatrix = matrices.getDistanceMatrix();
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
        StaticMatrices matrices = new StaticMatricesBuilder(tsp).build();
        int[][] distanceMatrix = matrices.getDistanceMatrix();

        int tourLen = getTourLen(tour, distanceMatrix);

        assertEquals(tourLen, 2754);
    }

    @Test
    public void testGr202Comparison() throws IOException {
        Tsp tsp = getTsp("tsplib/gr202.tsp");
        int[] tour = getTour("tsplib/gr202.opt.tour").getTour().get(0);
        StaticMatrices matrices = new StaticMatricesBuilder(tsp).build();
        int[][] distanceMatrix = matrices.getDistanceMatrix();

        int tourLen = getTourLen(tour, distanceMatrix);

        assertEquals(tourLen, 40215);
    }

    private int getTourLen(int[] tour, int[][] distanceMatrix) {
        int tourLen = 0;
        for (int i = 1; i < tour.length; i++) {
            tourLen += distanceMatrix[tour[i - 1] - 1][tour[i] - 1];
        }
        return tourLen;
    }

}
