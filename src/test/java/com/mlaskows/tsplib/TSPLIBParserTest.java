package com.mlaskows.tsplib;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by mlaskows on 21/04/2017.
 */
public class TSPLIBParserTest {

    @Test
    public void testUsa13509() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("usa13509.tsp").getFile());

        TSPLIBItem tsplibItem = TSPLIBParser.parse(file.getAbsolutePath());
        Assert.assertEquals(tsplibItem.getName(), "usa13509");
        Assert.assertEquals(tsplibItem.getDimension(), 13509);
        Assert.assertEquals(tsplibItem.getType(), TSPLIBType.TSP);
        Assert.assertEquals(tsplibItem.getEdgeWeightType(), EdgeWeightType.EUC_2D);
        Assert.assertEquals(tsplibItem.getNodes().size(), 13509);
    }

}
