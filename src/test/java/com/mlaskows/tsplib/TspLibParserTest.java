package com.mlaskows.tsplib;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by mlaskows on 21/04/2017.
 */
public class TspLibParserTest {

    @Test
    public void testUsa13509() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("usa13509.tsp").getFile());

        Item item = TspLibParser.parse(file.getAbsolutePath());

        Assert.assertEquals(item.getName(), "usa13509");
        Assert.assertEquals(item.getDimension(), 13509);
        Assert.assertEquals(item.getType(), Type.TSP);
        Assert.assertEquals(item.getEdgeWeightType(), EdgeWeightType.EUC_2D);
        Assert.assertEquals(item.getNodes().size(), 13509);
    }

    @Test
    public void testali535() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ali535.tsp").getFile());

        Item item = TspLibParser.parse(file.getAbsolutePath());

        Assert.assertEquals(item.getName(), "ali535");
        Assert.assertEquals(item.getDimension(), 535);
        Assert.assertEquals(item.getType(), Type.TSP);
        Assert.assertEquals(item.getEdgeWeightType(), EdgeWeightType.GEO);
        Assert.assertEquals(item.getDisplayDataType(), DisplayDataType.COORD_DISPLAY);
        Assert.assertEquals(item.getNodes().size(), 535);
        Assert.assertEquals(item.getComment(), "535 Airports around the globe (Padberg/Rinaldi)");

    }

}
