package com.mlaskows.tsplib;

import com.mlaskows.matrices.MatricesFactory;
import com.mlaskows.matrices.MatricesHolder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class MatricesFactoryTest {

    private static final int NN_FACOTR = 5;
    private static Item item;
    private static MatricesHolder matrices;

    @BeforeClass
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("australia.tsp")
                .getFile());
        item = TspLibParser.parse(file.getAbsolutePath());
        final MatricesFactory matricesFactory = new MatricesFactory(item, NN_FACOTR);
        matrices = matricesFactory.createMatrices();
    }

    @Test
    public void testDistancesAustralia() {
        int[][] distanceMatrix = matrices.getDistanceMatrix();

        Assert.assertEquals(distanceMatrix.length, item.getDimension());
        Assert.assertEquals(distanceMatrix[3][3], Integer.MAX_VALUE);
        // Brisbane - Melbourne (real 1372.50)
        Assert.assertEquals(distanceMatrix[0][1], 1371);
        // Sydney - Darwin (real 3144)
        Assert.assertEquals(distanceMatrix[2][5], 2928);

    }

    @Test
    public void testNNAustralia() {
        int[][] nearestNeighborList = matrices.getNearestNeighbors();
        Assert.assertEquals(nearestNeighborList.length, item.getDimension());
        Assert.assertEquals(nearestNeighborList[1].length, 5);
        Assert.assertEquals(nearestNeighborList[0][0], 2);
        Assert.assertEquals(nearestNeighborList[0][4], 3);
    }

    @Test
    public void testHeuristicAustralia() {
        double[][] heuristicInformationMatrix = matrices.getHeuristicInformationMatrix();
        Assert.assertEquals(heuristicInformationMatrix.length, item.getDimension());
        int i = matrices.getDistanceMatrix()[1][1];
        double v = 1.0 / ((double) i + 0.1);
        Assert.assertEquals(heuristicInformationMatrix[1][1], v);
    }

    @Test
    public void testPheromoneAustralia() {
        final double[][] pheromoneMatrix = matrices.getPheromoneMatrix();
        Assert.assertEquals(pheromoneMatrix[0][1], 0.0009844134536505333);
    }

}
