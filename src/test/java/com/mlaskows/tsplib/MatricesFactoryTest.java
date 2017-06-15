package com.mlaskows.tsplib;

import com.mlaskows.MatricesFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class MatricesFactoryTest {

    private static final int NN_FACOTR = 15;
    private static Item item;
    private static MatricesFactory matricesFactory;

    @BeforeClass
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("usa13509.tsp").getFile());
        item = TSPLIBParser.parse(file.getAbsolutePath());
        matricesFactory = new MatricesFactory(item, NN_FACOTR, initialTrail);
    }

    @Test
    public void testDistancesUsa13509() {
        int[][] distanceMatrix = matricesFactory.getDistanceMatrix();
        Assert.assertEquals(distanceMatrix.length, item.getDimension());
        Assert.assertEquals(distanceMatrix[3][3], Integer.MAX_VALUE);
    }

    @Test
    public void testNNUsa13509() {
        int[][] nearestNeighborList = matricesFactory.getNearestNeighborList();
        Assert.assertEquals(nearestNeighborList.length, item.getDimension());
        Assert.assertEquals(nearestNeighborList[1].length, NN_FACOTR);
    }

    @Test
    public void testHeuristicUsa13509() {
        double[][] heuristicInformationMatrix = matricesFactory.getHeuristicInformationMatrix();
        Assert.assertEquals(heuristicInformationMatrix.length, item.getDimension());
        int i = matricesFactory.getDistanceMatrix()[1][1];
        double v = 1.0 / ((double) i + 0.1);
        Assert.assertEquals(heuristicInformationMatrix[1][1], v);
    }

}
