package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSystemSolver extends AbstractAntSolver implements Solver {

    private Ant bestSoFarAnt;

    public MinMaxAntSystemSolver(AcoConfig config, StaticMatricesHolder matrices) {
        super(config, matrices);
    }

    @Override
    public double calculateInitialPheromoneValue() {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new  NearestNeighbourSolver(getMatrices());
        final Solution solution = nearestNeighbourSolver.getSolution();
        return (double) 1 /
                getConfig().getPheromoneEvaporationFactor() *
                solution .getTourLength();
    }

    @Override
    public Solution getSolution() {
        int iterationsWithNoImprovementCount = 0;
        Ant bestAnt;
        while (shouldNotTerminate(iterationsWithNoImprovementCount)) {
            initializeRandomPlacedAnts(getProblemSize());
            computeChoicesInfo();
            constructSolution();
            bestAnt = getBestAnt();
            getStatisticsBuilder().addIterationTourLength(bestAnt.getTourLength());
            if (bestSoFarAnt == null ||
                    bestAnt.getTourLength() < bestSoFarAnt.getTourLength()) {
                bestSoFarAnt = bestAnt;
                iterationsWithNoImprovementCount = 0;
            } else {
                iterationsWithNoImprovementCount++;
                if (iterationsWithNoImprovementCount > getConfig()
                        .getMaxStagnationCount() / 2) {
                    reinitialize();
                    continue;
                }
            }
            // FIXME Idk if it's good but it can save some effort
            if (!shouldNotTerminate(iterationsWithNoImprovementCount)) {
                break;
            }
            evaporatePheromone();
            // TODO improve choosing ant and local search
            final Ant ant = getRandom().nextBoolean() ? bestAnt : bestSoFarAnt;
            final Solution improvedAntSolution =
                    new TwoOptSolver(ant.getSolution(), getMatrices()).getSolution();
            depositPheromone(new Ant(improvedAntSolution));
        }
        return bestSoFarAnt.getSolution();
    }

    private void reinitialize() {
        initPheromone(calculateInitialPheromoneValue());
    }

    private void depositPheromone(Ant ant) {
        final double pheromoneDeltaCandidate = (double) 1 / ant.getTourLength();
        double pheromoneDelta = pheromoneDeltaCandidate > 1.0 ?
                1.0 : pheromoneDeltaCandidate;
        depositAntPheromone(ant, pheromoneDelta);
    }

}
