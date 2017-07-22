package com.mlaskows.solvers;

import com.mlaskows.config.AcoConfig;
import com.mlaskows.datamodel.Ant;
import com.mlaskows.datamodel.matrices.StaticMatricesHolder;

import static com.mlaskows.exeptions.Reason.EMPTY_HEURISTIC_MATRIX;

public class PheromoneProcessor {

    private final int problemSize;
    private final AcoConfig config;
    private final double[][] heuristicInformationMatrix;
    private double[][] pheromoneMatrix;
    private double[][] choicesInfo;

    public PheromoneProcessor(StaticMatricesHolder matrices, AcoConfig config) {
        this.config = config;
        problemSize = matrices.getProblemSize();
        heuristicInformationMatrix = matrices.getHeuristicInformationMatrix()
                .orElseThrow(() -> new IllegalArgumentException(EMPTY_HEURISTIC_MATRIX.toString()));
        pheromoneMatrix = new double[problemSize][problemSize];
        choicesInfo = new double[problemSize][problemSize];
    }

    public void initPheromone(double initialPheromoneValue) {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                updatePheromoneOnEdge(i, j, initialPheromoneValue);
            }
        }
    }

    public void evaporatePheromone() {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                double value =
                        (1 - config.getPheromoneEvaporationFactor()) *
                                pheromoneMatrix[i][j];
                updatePheromoneOnEdge(i, j, value);
            }
        }
    }

    public void depositAntPheromone(Ant ant, double pheromoneDelta) {
        for (int i = 0; i < ant.getTour().size() - 1; i++) {
            int j = ant.getTour().get(i);
            int l = ant.getTour().get(i + 1);
            updatePheromoneOnEdge(j, l, pheromoneMatrix[j][l] + pheromoneDelta);
        }
    }

    protected double[][] computeChoicesInfo() {
        for (int i = 0; i < problemSize; i++) {
            for (int j = i; j < problemSize; j++) {
                final double choice =
                        Math.pow(pheromoneMatrix[i][j], config.getPheromoneImportance()) *
                                Math.pow(heuristicInformationMatrix[i][j], config.getHeuristicImportance());
                choicesInfo[i][j] = choice;
                choicesInfo[j][i] = choice;
            }
        }
        return choicesInfo;
    }

    private void updatePheromoneOnEdge(int from, int to, double pheromoneValue) {
        pheromoneMatrix[from][to] = pheromoneValue;
        pheromoneMatrix[to][from] = pheromoneValue;
    }

}
