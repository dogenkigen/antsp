package com.mlaskows.tsplib;

import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.types.DisplayDataType;
import com.mlaskows.tsplib.types.EdgeWeightType;
import com.mlaskows.tsplib.types.Type;
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

        Tsp tsp = TspLibParser.parse(file.getAbsolutePath());

        Assert.assertEquals(tsp.getName(), "usa13509");
        Assert.assertEquals(tsp.getDimension(), 13509);
        Assert.assertEquals(tsp.getType(), Type.TSP);
        Assert.assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EUC_2D);
        Assert.assertEquals(tsp.getNodes().size(), 13509);
    }

    @Test
    public void testali535() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ali535.tsp").getFile());

        Tsp tsp = TspLibParser.parse(file.getAbsolutePath());

        Assert.assertEquals(tsp.getName(), "ali535");
        Assert.assertEquals(tsp.getDimension(), 535);
        Assert.assertEquals(tsp.getType(), Type.TSP);
        Assert.assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.GEO);
        Assert.assertEquals(tsp.getDisplayDataType(), DisplayDataType.COORD_DISPLAY);
        Assert.assertEquals(tsp.getNodes().size(), 535);
        Assert.assertEquals(tsp.getComment(), "535 Airports around the globe (Padberg/Rinaldi)");

    }

}
