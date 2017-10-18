package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.exeptions.Reason;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Ant;

public class PheromoneProcessor {

    private final int problemSize;
    private final AcoConfig config;
    private final double[][] heuristicInformationMatrix;
    private double[][] pheromoneMatrix;
    private double[][] choicesInfo;

    public PheromoneProcessor(StaticData data, AcoConfig config) {
        this.config = config;
        problemSize = data.getProblemSize();
        heuristicInformationMatrix = data.getHeuristicInformationMatrix()
                .orElseThrow(() -> new IllegalArgumentException(Reason.EMPTY_HEURISTIC_MATRIX.toString()));
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
            int from = ant.getTour().get(i);
            int to = ant.getTour().get(i + 1);
            updatePheromoneOnEdge(from, to, pheromoneMatrix[from][to] + pheromoneDelta);
        }
    }

    public double[][] computeChoicesInfo() {
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
