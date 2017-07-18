package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSystemSolver extends AbstractAntSolver implements Solver {

    private Ant bestSoFarAnt;
    private double minPheromoneValue;
    private double maxPheromoneValue;
    private double a = 4;

    public MinMaxAntSystemSolver(AcoConfig config, StaticMatricesHolder matrices) {
        super(config, matrices);
    }

    @Override
    public double calculateInitialPheromoneValue() {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(getMatrices());
        final Solution solution = nearestNeighbourSolver.getSolution();
        bestSoFarAnt = new Ant(solution);
        return (double) 1 /
                getConfig().getPheromoneEvaporationFactor() *
                solution.getTourLength();
    }

    @Override
    public Solution getSolution() {
        int iterationsWithNoImprovementCount = 0;
        Ant bestAnt;
        while (shouldNotTerminate(iterationsWithNoImprovementCount)) {
            initializeRandomPlacedAnts(getProblemSize());
            computeChoicesInfo();
            constructSolution();

            System.out.println("waiting for 2opt...");
            final long l = System.currentTimeMillis();

            final List<Ant> bestAnts = getAnts().stream()
                    .sorted()
                    .limit(Runtime.getRuntime().availableProcessors())
                    .parallel()
                    .map(ant -> new TwoOptSolver(ant.getSolution(), getMatrices()).getSolution())
                    .map(solution -> new Ant(solution))
                    .collect(Collectors.toList());

            System.out.println("2opt done after " +
                    (System.currentTimeMillis() - l));

            bestAnt = getBestAnt(bestAnts);
            getStatisticsBuilder().addIterationTourLength(bestAnt.getTourLength());
            if (bestAnt.getTourLength() < bestSoFarAnt.getTourLength()) {
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
            System.out.println("It with no improvement " +
                    iterationsWithNoImprovementCount + " best ant tour " +
                    bestSoFarAnt.getTourLength());

            // FIXME Idk if it's good but it can save some effort
            if (!shouldNotTerminate(iterationsWithNoImprovementCount)) {
                break;
            }
            updateMinMax();
            evaporatePheromone();
            // TODO improve choosing ant and local search
            final Ant ant = getRandom().nextBoolean() ? bestAnt : bestSoFarAnt;
            depositPheromone(ant);
        }
        return bestSoFarAnt.getSolution();
    }

    private void updateMinMax() {
        maxPheromoneValue = (double) 1 /
                getConfig().getPheromoneEvaporationFactor() *
                bestSoFarAnt.getTourLength();
        minPheromoneValue = maxPheromoneValue / a;
    }

    private void reinitialize() {
        initPheromone(calculateInitialPheromoneValue());
    }

    private void depositPheromone(Ant ant) {
        final double pheromoneDeltaCandidate = (double) 1 / ant.getTourLength();
        double pheromoneDelta = pheromoneDeltaCandidate > maxPheromoneValue ?
                maxPheromoneValue : pheromoneDeltaCandidate;
        pheromoneDelta = pheromoneDelta < minPheromoneValue ?
                minPheromoneValue : pheromoneDeltaCandidate;
        depositAntPheromone(ant, pheromoneDelta);
    }

}
