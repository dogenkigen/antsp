package com.mlaskows.tsplib;

import com.mlaskows.AlgorithmType;
import com.mlaskows.InitialPheromoneValueFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by mlaskows on 18/06/2017.
 */
public class InitialPheromoneValueFactoryTest {
    private final int distances[][] = new int[3][3];

    @BeforeClass
    public void init() {
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                distances[i][j] = 2;
            }
        }
    }

    @Test
    public void testForAntSystem() {
        final InitialPheromoneValueFactory initialPheromoneValueFactory = new
                InitialPheromoneValueFactory(distances);
        final double initialPheromoneValue = initialPheromoneValueFactory.calculateInitialPheromoneValue
                (AlgorithmType.ANT_SYSTEM);
        Assert.assertEquals(initialPheromoneValue, 0.75);
    }

}
