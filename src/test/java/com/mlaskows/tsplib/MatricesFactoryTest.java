package com.mlaskows.tsplib;

import com.mlaskows.MatricesFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class MatricesFactoryTest {

    @Test
    public void testDistancesUsa13509() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("usa13509.tsp").getFile());
        Item item = TSPLIBParser.parse(file.getAbsolutePath());

        MatricesFactory matricesFactory = new MatricesFactory(item);
        int[][] distanceMatrix = matricesFactory.getDistanceMatrix();

        Assert.assertEquals(distanceMatrix[3][3], Integer.MAX_VALUE);

    }

}
