package com.mlaskows.statistics;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by maciej_laskowski on 13.07.2017.
 */
public class StatisticsTest {

    private Statistics statistic;

    @BeforeClass
    public void init() {
        StatisticsBuilder builder = new StatisticsBuilder();
        int tourLenght = 10000;
        // Do improvement for every prime number iteration index
        for (int i = 0; i < 47; i++) {
            if (isPrime(i)) {
                tourLenght = tourLenght - i;
            }
            builder.addIterationTourLength(tourLenght);
        }

        statistic = builder.build();
    }

    @Test
    public void testNonImprovementPeriods() {
        // Since improvements are done for every prime number iteration
        // and breaks between prime numbers are also prime number
        // we can make assumption like this:
        Assert.assertTrue(statistic.getNonImprovementPeriods().stream()
                .allMatch(StatisticsTest::isPrime));
    }

    @Test
    public void testIterationIndexForBestSolution() {
        Assert.assertEquals(statistic.getIterationIndexForBestSolution(), 43);
    }

    private static boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0) return false;
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

}
