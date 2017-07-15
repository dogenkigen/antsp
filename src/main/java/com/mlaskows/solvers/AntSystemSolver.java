package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.statistics.Statistics;
import com.mlaskows.statistics.StatisticsBuilder;

/**
 * Created by mlaskows on 24/06/2017.
 */
// TODO matrices should be immutable so good idea would be to wrap them into
// class
public class AntSystemSolver extends AbstractAntSolver implements Solver {

    private Ant bestSoFarAnt;
    private final StatisticsBuilder statisticsBuilder = new StatisticsBuilder();

    public AntSystemSolver(AcoConfig config, StaticMatricesHolder matrices) {
        super(config, matrices);
        initializeRandomPlacedAnts(getProblemSize());
        initPheromone();
    }

    private void initPheromone() {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new  NearestNeighbourSolver(getMatrices());
        final Solution solution = nearestNeighbourSolver.getSolution();
        final double initialPheromoneValue =
                (double) getMatrices().getDistanceMatrix().length /
                solution.getTourLength();
        for (int i = 0; i < getProblemSize(); i++) {
            for (int j = i; j < getProblemSize(); j++) {
                updatePheromoneOnEdge(i, j, initialPheromoneValue);
            }
        }
    }

    @Override
    public Solution getSolution() {
        int iterationsWithNoImprovementCount = 0;
        Ant bestAnt;
        while (shouldNotTerminate(iterationsWithNoImprovementCount)) {
            initializeRandomPlacedAnts(getProblemSize());
            computeChoicesInfo();
            constructSolution();
            updatePheromone();
            bestAnt = getBestAnt();
            statisticsBuilder.addIterationTourLength(bestAnt.getTourLength());
            if (bestSoFarAnt == null ||
                    bestAnt.getTourLength() < bestSoFarAnt.getTourLength()) {
                bestSoFarAnt = bestAnt;
                iterationsWithNoImprovementCount = 0;
            } else {
                iterationsWithNoImprovementCount++;
            }
        }
        return bestSoFarAnt.getSolution();
    }

    private boolean shouldNotTerminate(int iterationsWithNoImprovementCount) {
        return iterationsWithNoImprovementCount < getConfig().getMaxStagnationCount();
    }

    private void updatePheromone() {
        evaporatePheromone();
        depositAllAntsPheromone();
    }

    private void depositAllAntsPheromone() {
        getAnts().forEach(ant -> depositAntPheromone(ant));
    }

    public Statistics getStatistics() {
        return statisticsBuilder.build();
    }
}
