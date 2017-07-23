package com.mlaskows.solvers.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.NearestNeighbourSolver;
import com.mlaskows.solvers.ant.util.PheromoneProcessor;
import com.mlaskows.solvers.Solver;

import java.util.Optional;

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
            pheromoneProcessor.evaporatePheromone();
            depositPheromone();
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

    protected void depositPheromone() {
        depositAllAntsPheromone();
    }

    private void depositAllAntsPheromone() {
        getAnts().forEach(ant -> depositAntPheromone(ant, (double) 1 / ant.getTourLength()));
    }

    protected Optional<Ant> getBestSoFarAnt() {
        return Optional.ofNullable(bestSoFarAnt);
    }

    protected void depositAntPheromone(Ant ant, double pheromoneDelta) {
        // TODO it can be refactored to just pass nominator since pheromone
        // delta is always computed as x / ant.tourLen
        pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
    }
}
