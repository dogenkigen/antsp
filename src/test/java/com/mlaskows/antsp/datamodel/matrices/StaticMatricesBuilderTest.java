package com.mlaskows.antsp.datamodel.matrices;

import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.TspLibParser;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class StaticMatricesBuilderTest {

    private static final int NN_FACOTR = 5;
    private static Tsp tsp;

    @BeforeClass
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("australia.tsp")
                .getFile());
        tsp = TspLibParser.parse(file.getAbsolutePath());
    }

    @Test
    public void testDistancesAustralia() {
        StaticMatrices matricesHolder = new StaticMatricesBuilder(tsp).build();
        int[][] distanceMatrix = matricesHolder.getDistanceMatrix();

        Assert.assertEquals(distanceMatrix.length, tsp.getDimension());
        Assert.assertEquals(distanceMatrix[3][3], Integer.MAX_VALUE);
        // Brisbane - Melbourne (real 1372.50)
        Assert.assertEquals(distanceMatrix[0][1], 1371);
        // Sydney - Darwin (real 3144)
        Assert.assertEquals(distanceMatrix[2][5], 2928);
    }

    @Test
    public void testNNAustralia() {
        StaticMatrices matricesHolder = new StaticMatricesBuilder(tsp)
                .withNearestNeighbors(NN_FACOTR)
                .build();
        int[][] nearestNeighborList = matricesHolder.getNearestNeighborsMatrix().orElseThrow(RuntimeException::new);
        Assert.assertEquals(nearestNeighborList.length, tsp.getDimension());
        Assert.assertEquals(nearestNeighborList[1].length, 5);
        Assert.assertEquals(nearestNeighborList[0][0], 2);
        Assert.assertEquals(nearestNeighborList[0][4], 3);
    }

    @Test
    public void testHeuristicAustralia() {
        StaticMatrices matricesHolder = new StaticMatricesBuilder(tsp)
                .withHeuristicInformationMatrix()
                .build();
        double[][] heuristicInformationMatrix = matricesHolder.getHeuristicInformationMatrix().orElseThrow(RuntimeException::new);
        Assert.assertEquals(heuristicInformationMatrix.length, tsp.getDimension());
        int i = matricesHolder.getDistanceMatrix()[1][1];
        double v = 1.0 / ((double) i + 0.1);
        Assert.assertEquals(heuristicInformationMatrix[1][1], v);
    }

}
