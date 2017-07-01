package com.mlaskows.tsplib;

public class DataProvider {

    private DataProvider() {
    }

    public static int[][] getAustralianDistances() {
        final int[][] distances = {{2147483647, 1371, 636, 4225, 1908, 2850},
                {1371, 2147483647, 853, 3255, 1090, 2553},
                {636, 853, 2147483647, 3999, 1694, 2928},
                {4225, 3255, 3999, 2147483647, 2327, 2008},
                {1908, 1090, 1694, 2327, 2147483647, 1508},
                {2850, 2553, 2928, 2008, 1508, 2147483647}};
        return distances;
    }
}