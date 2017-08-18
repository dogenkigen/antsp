package com.mlaskows.tsplib;

import com.mlaskows.BaseWithTspTest;
import com.mlaskows.tsplib.datamodel.Tsp;
import com.mlaskows.tsplib.datamodel.types.DisplayDataType;
import com.mlaskows.tsplib.datamodel.types.EdgeWeightFormat;
import com.mlaskows.tsplib.datamodel.types.EdgeWeightType;
import com.mlaskows.tsplib.datamodel.types.Type;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

/**
 * Created by mlaskows on 21/04/2017.
 */
public class TspLibParserTest implements BaseWithTspTest {

    @Test
    public void testUsa13509() throws IOException {
        Tsp tsp = getTsp("usa13509.tsp");

        assertEquals(tsp.getName(), "usa13509");
        assertEquals(tsp.getDimension(), 13509);
        assertEquals(tsp.getType(), Type.TSP);
        Assert.assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EUC_2D);
        assertTrue(tsp.getNodes().isPresent());
        assertEquals(tsp.getNodes().get().size(), 13509);
    }

    @Test
    public void testAli535() throws IOException {
        Tsp tsp = getTsp("ali535.tsp");

        assertEquals(tsp.getName(), "ali535");
        assertEquals(tsp.getDimension(), 535);
        assertEquals(tsp.getType(), Type.TSP);
        Assert.assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.GEO);
        Assert.assertEquals(tsp.getDisplayDataType(), DisplayDataType.COORD_DISPLAY);
        assertTrue(tsp.getNodes().isPresent());
        assertEquals(tsp.getNodes().get().size(), 535);
        assertEquals(tsp.getComment(), "535 Airports around the globe (Padberg/Rinaldi)");
    }

    @Test
    public void testBerlin52() throws IOException {
        final Tsp tsp = getTsp("berlin52.tsp");

        assertEquals(tsp.getName(), "berlin52");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getComment(), "52 locations in Berlin (Groetschel)");
        assertEquals(tsp.getDimension(), 52);
        Assert.assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EUC_2D);
    }

    @Test
    public void testAtt532() throws IOException {
        final Tsp tsp = getTsp("att532.tsp");

        assertEquals(tsp.getName(), "att532");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getComment(), "532-city problem (Padberg/Rinaldi)");
        assertEquals(tsp.getDimension(), 532);
        assertTrue(tsp.getNodes().isPresent());
        assertEquals(tsp.getNodes().get().size(), 532);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.ATT);
    }

    @Test
    public void testSwiss42() throws IOException {
        final Tsp tsp = getTsp("swiss42.tsp");

        assertEquals(tsp.getName(), "swiss42");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EXPLICIT);
        assertEquals(tsp.getEdgeWeightFormat(), EdgeWeightFormat.FULL_MATRIX);
        assertEquals(tsp.getDimension(), 42);
        assertTrue(tsp.getEdgeWeightData().isPresent());
        final int[][] data = tsp.getEdgeWeightData().get();
        assertEquals(data[0][0], 0);
        assertEquals(data[0][1], 15);
        assertEquals(data[41][40], 81);
    }

    @Test
    public void testBays29() throws IOException {
        final Tsp tsp = getTsp("bays29.tsp");

        assertEquals(tsp.getName(), "bays29");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EXPLICIT);
        assertEquals(tsp.getEdgeWeightFormat(), EdgeWeightFormat.FULL_MATRIX);
        assertEquals(tsp.getDisplayDataType(), DisplayDataType.TWOD_DISPLAY);
        assertEquals(tsp.getDimension(), 29);
        assertTrue(tsp.getEdgeWeightData().isPresent());
        assertTrue(tsp.getNodes().isPresent());
        assertEquals(tsp.getNodes().get().size(), 29);
    }

    @Test
    public void testDantzig42() throws IOException {
        final Tsp tsp = getTsp("dantzig42.tsp");

        assertEquals(tsp.getName(), "dantzig42");
        assertEquals(tsp.getType(), Type.TSP);
        assertEquals(tsp.getEdgeWeightType(), EdgeWeightType.EXPLICIT);
        assertEquals(tsp.getEdgeWeightFormat(), EdgeWeightFormat.LOWER_DIAG_ROW);
        assertEquals(tsp.getDisplayDataType(), DisplayDataType.TWOD_DISPLAY);
        assertEquals(tsp.getDimension(), 42);
        assertTrue(tsp.getEdgeWeightData().isPresent());
        final int[][] ints = tsp.getEdgeWeightData().get();
        final int[] expected = {5, 12, 55, 41, 53, 64, 61, 61, 66, 84, 111, 113, 150,
                186, 192, 166, 147, 180, 188, 167, 140, 124, 119, 90, 87, 90, 94,
                107, 114, 77, 86, 92, 98, 80, 74, 77, 60, 48, 38, 32, 6, 0};
        assertArrayEquals(ints[41], expected);
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints.length; j++) {
                assertEquals(ints[i][j], ints[j][i]);
            }
        }
        assertTrue(tsp.getNodes().isPresent());
        assertEquals(tsp.getNodes().get().size(), 42);
    }

    @Test
    public void testAll() throws IOException {
        final List<String> tsps = Files.list(Paths.get("./tsplib_bak"))
                .map(path -> path.toAbsolutePath())
                .map(Path::toString)
                .filter(s -> s.endsWith("tsp"))
                .filter(s -> s.contains("dantzig"))
                .collect(toList());
        for (String path : tsps) {
            try {
                TspLibParser.parse(path);
            } catch (Exception e) {
                System.out.println(path + " " + e.getMessage());
            }
        }
    }

}
