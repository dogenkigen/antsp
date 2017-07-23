package com.mlaskows.solvers.ant;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.NearestNeighbourSolver;
import com.mlaskows.solvers.ant.util.PheromoneProcessor;
import com.mlaskows.solvers.Solver;
import com.mlaskows.solvers.TwoOptSolver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mlaskows on 15/07/2017.
 */
public class MinMaxAntSystemSolver extends AbstractAntSolver implements Solver {

    private Ant bestSoFarAnt;
    private double minPheromoneValue;
    private double maxPheromoneValue;
    private final PheromoneProcessor pheromoneProcessor;

    public MinMaxAntSystemSolver(StaticMatricesHolder matrices, AcoConfig config) {
        super(matrices, config);
        pheromoneProcessor = new PheromoneProcessor(matrices, config);
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(getMatrices());
        final Solution solution = nearestNeighbourSolver.getSolution();
        bestSoFarAnt = new Ant(solution);
        pheromoneProcessor.initPheromone(calculateInitialPheromoneValue());
    }

    @Override
    public double calculateInitialPheromoneValue() {
        return (double) 1 /
                getConfig().getPheromoneEvaporationFactor() *
                bestSoFarAnt.getTourLength();
    }

    @Override
    public Solution getSolution() {
        int iterationsWithNoImprovementCount = 0;
        Ant bestAnt;
        while (shouldNotTerminate(iterationsWithNoImprovementCount)) {
            initializeRandomPlacedAnts(getProblemSize());
            constructSolution(pheromoneProcessor.computeChoicesInfo());

            bestAnt = getBestAntAfterLocalSearch();
            getStatisticsBuilder().addIterationTourLength(bestAnt.getTourLength());
            if (bestAnt.getTourLength() < bestSoFarAnt.getTourLength()) {
                bestSoFarAnt = bestAnt;
                iterationsWithNoImprovementCount = 0;
            } else {
                iterationsWithNoImprovementCount++;
                if (iterationsWithNoImprovementCount == getConfig()
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
            // TODO improve choosing ant and local search
            final Ant ant = getRandom().nextBoolean() ? bestAnt : bestSoFarAnt;
            updatePheromone(ant);
        }
        return bestSoFarAnt.getSolution();
    }

    private Ant getBestAntAfterLocalSearch() {
        Ant bestAnt;
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
        return bestAnt;
    }

    private void reinitialize() {
        pheromoneProcessor.initPheromone(calculateInitialPheromoneValue());
    }

    private void updateMinMax() {
        maxPheromoneValue = (double) 1 /
                getConfig().getPheromoneEvaporationFactor() *
                bestSoFarAnt.getTourLength();
        minPheromoneValue = maxPheromoneValue / getConfig().getMinPheromoneLimitDivider();
    }

    private void updatePheromone(Ant ant) {
        updateMinMax();
        pheromoneProcessor.evaporatePheromone();
        depositPheromone(ant);
    }

    private void depositPheromone(Ant ant) {
        final double pheromoneDeltaCandidate = (double) 1 / ant.getTourLength();
        double pheromoneDelta = pheromoneDeltaCandidate > maxPheromoneValue ?
                maxPheromoneValue : pheromoneDeltaCandidate;
        pheromoneDelta = pheromoneDelta < minPheromoneValue ?
                minPheromoneValue : pheromoneDeltaCandidate;
        pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
    }

}
