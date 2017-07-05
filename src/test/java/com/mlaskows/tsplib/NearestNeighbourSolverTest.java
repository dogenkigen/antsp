package com.mlaskows.tsplib;

import com.mlaskows.matrices.StaticMatricesBuilder;
import com.mlaskows.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.NearestNeighbourSolver;
import com.mlaskows.datamodel.Solution;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class NearestNeighbourSolverTest {


    @Test
    public void testSolution() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("australia.tsp")
                .getFile());
        final Item item = TspLibParser.parse(file.getAbsolutePath());
        StaticMatricesHolder matricesHolder = new StaticMatricesBuilder(item).build();

        final NearestNeighbourSolver solver = new NearestNeighbourSolver
                (matricesHolder);
        final Solution solution = solver.getSolution();

        final List<Integer> expectedTour = List.of(0, 2, 1, 4, 5, 3);

        Assert.assertEquals(solution.getTour(), expectedTour);
        Assert.assertEquals(solution.getTourLength(), 6095);
    }

}
