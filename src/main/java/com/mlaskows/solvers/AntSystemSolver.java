package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

/**
 * Created by mlaskows on 24/06/2017.
 */
// TODO matrices should be immutable so good idea would be to wrap them into
// class
public class AntSystemSolver extends AbstractAntSolver implements Solver {

    private Ant bestSoFarAnt;
    private final PheromoneProcessor pheromoneProcessor;

    public AntSystemSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
        pheromoneProcessor = new PheromoneProcessor(matrices, config);
        pheromoneProcessor.initPheromone(calculateInitialPheromoneValue());
    }

    @Override
    public double calculateInitialPheromoneValue() {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new  NearestNeighbourSolver(getMatrices());
        final Solution solution = nearestNeighbourSolver.getSolution();
        return (double) getMatrices().getDistanceMatrix().length /
                        solution.getTourLength();
    }

    @Override
    public Solution getSolution() {
        int iterationsWithNoImprovementCount = 0;
        Ant bestAnt;
        while (shouldNotTerminate(iterationsWithNoImprovementCount)) {
            initializeRandomPlacedAnts(getProblemSize());
            constructSolution(pheromoneProcessor.computeChoicesInfo());
            updatePheromone();
            bestAnt = getBestAnt();
            getStatisticsBuilder().addIterationTourLength(bestAnt.getTourLength());
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

    private void updatePheromone() {
        pheromoneProcessor.evaporatePheromone();
        depositAllAntsPheromone();
    }

    private void depositAllAntsPheromone() {
        getAnts().forEach(ant ->
                pheromoneProcessor.depositAntPheromone(ant, (double) 1 / ant.getTourLength()));
    }

}
