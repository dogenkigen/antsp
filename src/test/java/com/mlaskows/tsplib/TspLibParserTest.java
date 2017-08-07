package com.mlaskows.tsplib;

import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.datamodel.types.DisplayDataType;
import com.mlaskows.tsplib.datamodel.types.EdgeWeightType;
import com.mlaskows.tsplib.datamodel.types.Type;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

/**
 * Created by mlaskows on 21/04/2017.
 */
public class TspLibParserTest {

    @Test
    public void testUsa13509() throws IOException {
        Tsp tsp = getTsp("usa13509.tsp");

        assertEquals(tsp.getName(), "usa13509");
        assertEquals(tsp.getDimension(), 13509);
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EUC_2D);
        assertEquals(tsp.getNodes().size(), 13509);
    }

    @Test
    public void testAli535() throws IOException {
        Tsp tsp = getTsp("ali535.tsp");

        assertEquals(tsp.getName(), "ali535");
        assertEquals(tsp.getDimension(), 535);
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.GEO);
        assertEquals(tsp.getDisplayDataType(), DisplayDataType.COORD_DISPLAY);
        assertEquals(tsp.getNodes().size(), 535);
        assertEquals(tsp.getComment(), "535 Airports around the globe (Padberg/Rinaldi)");
    }

    @Test
    public void testBerlin52() throws IOException {
        final Tsp tsp = getTsp("berlin52.tsp");

        assertEquals(tsp.getName(), "berlin52");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getComment(), "52 locations in Berlin (Groetschel)");
        assertEquals(tsp.getDimension(), 52);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EUC_2D);
    }

    @Test
    public void testAtt532() throws IOException {
        final Tsp tsp = getTsp("att532.tsp");

        assertEquals(tsp.getName(), "att532");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getComment(), "532-city problem (Padberg/Rinaldi)");
        assertEquals(tsp.getDimension(), 532);
        assertEquals(tsp.getNodes().size(), 532);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.ATT);
    }

    private Tsp getTsp(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        return TspLibParser.parse(file.getAbsolutePath());
    }

}
