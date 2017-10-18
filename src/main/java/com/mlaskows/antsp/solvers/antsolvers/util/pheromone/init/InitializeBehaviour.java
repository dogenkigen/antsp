package com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init;

import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.Solution;
import com.mlaskows.antsp.exeptions.Reason;
import com.mlaskows.antsp.exeptions.SolutionException;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.PheromoneProcessor;

public interface InitializeBehaviour {

    void initialize(PheromoneProcessor pheromoneProcessor, StaticData data, AcoConfig config);

    default Solution getExtractHeuristicSolution(StaticData data) {
        return data.getHeuristicSolution()
                .orElseThrow(() -> new SolutionException(Reason.NO_HEURISTIC_SOLUTION));
    }

}
