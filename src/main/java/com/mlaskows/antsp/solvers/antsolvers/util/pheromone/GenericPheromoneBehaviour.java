/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mlaskows.antsp.solvers.antsolvers.util.pheromone;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.data.StaticData;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.deposit.DepositBehaviour;
import com.mlaskows.antsp.solvers.antsolvers.util.pheromone.init.InitializeBehaviour;

public abstract class GenericPheromoneBehaviour {

    private final PheromoneProcessor pheromoneProcessor;
    private final StaticData data;
    private final DepositBehaviour depositBehaviour;
    private final InitializeBehaviour initializeBehaviour;
    private final AcoConfig config;

    public GenericPheromoneBehaviour(StaticData data,
                                     AcoConfig config,
                                     DepositBehaviour depositBehaviour,
                                     InitializeBehaviour initializeBehaviour) {
        this.pheromoneProcessor = new PheromoneProcessor(data, config);
        this.data = data;
        this.config = config;
        this.depositBehaviour = depositBehaviour;
        this.initializeBehaviour = initializeBehaviour;
    }

    public void initializePheromone() {
        initializeBehaviour.initialize(pheromoneProcessor, data, config);
    }

    public double[][] getChoicesInfo() {
        return pheromoneProcessor.computeChoicesInfo();
    }

    public void evaporatePheromone() {
        pheromoneProcessor.evaporatePheromone();
    }

    public void depositPheromone(IterationResult iterationResult) {
        depositBehaviour.deposit(pheromoneProcessor, iterationResult);
    }

}
