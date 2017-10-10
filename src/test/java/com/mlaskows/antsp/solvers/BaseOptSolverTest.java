package com.mlaskows.antsp.solvers;

import com.mlaskows.BaseWithTspTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseOptSolverTest implements BaseWithTspTest {

    protected List<Integer> getInitialTour(int size) {
        return IntStream.concat(IntStream.range(0, size), IntStream.of(0))
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
    }

    protected int calculateDistance(int[][] australianDistances, List<Integer>
            initialTour) {
        int distance = 0;
        for (int i = 0; i < initialTour.size() - 1; i++) {
            distance += australianDistances[initialTour.get(i)][initialTour.get(i + 1)];
        }
        return distance;
    }

}
