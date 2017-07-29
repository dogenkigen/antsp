package com.mlaskows.solvers.antsolvers.util.pheromone;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.IterationResult;
import com.mlaskows.datamodel.Solution;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;
import com.mlaskows.solvers.heuristic.NearestNeighbourSolver;

public class AntSystemPheromoneBehaviour implements PheromoneBehaviour {

    private final PheromoneProcessor pheromoneProcessor;
    private final StaticMatricesHolder matrices;

    public AntSystemPheromoneBehaviour(StaticMatricesHolder matrices, AcoConfig config) {
        this.pheromoneProcessor = new PheromoneProcessor(matrices, config);
        this.matrices = matrices;
    }

    @Override
    public void initializePheromone() {
        pheromoneProcessor.initPheromone(calculateInitialPheromoneValue(matrices));
    }

    private double calculateInitialPheromoneValue(StaticMatricesHolder matrices) {
        final NearestNeighbourSolver nearestNeighbourSolver =
                new NearestNeighbourSolver(matrices);
        final Solution solution = nearestNeighbourSolver.getSolution();
        return (double) matrices.getProblemSize() /
                solution.getTourLength();
    }

    @Override
    public double[][] getChoicesInfo() {
        return pheromoneProcessor.computeChoicesInfo();
    }

    @Override
    public void evaporatePheromone() {
        pheromoneProcessor.evaporatePheromone();
    }

    @Override
    public void depositPheromone(IterationResult iterationResult) {
        iterationResult.getSortedAnts()
                .forEach(ant ->
                        // TODO it can be refactored to just pass nominator since pheromone
                        // delta is always computed as x / ant.tourLen
                        depositAntPheromone(ant, (double) 1 / ant.getTourLength()));
    }

    protected void depositAntPheromone(Ant ant, double pheromoneDelta) {
        pheromoneProcessor.depositAntPheromone(ant, pheromoneDelta);
    }
}
