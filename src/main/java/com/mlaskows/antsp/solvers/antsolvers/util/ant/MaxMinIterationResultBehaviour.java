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

package com.mlaskows.antsp.solvers.antsolvers.util.ant;

import com.mlaskows.antsp.config.AcoConfig;
import com.mlaskows.antsp.config.MaxMinConfig;
import com.mlaskows.antsp.datamodel.IterationResult;
import com.mlaskows.antsp.datamodel.matrices.StaticMatrices;

public class MaxMinIterationResultBehaviour extends IterationResultBehaviour {

    private final MaxMinConfig config;

    public MaxMinIterationResultBehaviour(StaticMatrices matrices, AcoConfig config) {
        super(new IterationResultFactory(matrices,config));
        this.config = (MaxMinConfig) config;
    }

    @Override
    public IterationResult getIterationResult(double[][] choicesInfo) {
        final IterationResult iterationResult = super.getIterationResult(choicesInfo);
        if (iterationResult.getStagnationCount() == config.getReinitializationCount()) {
            getIterationResultFactory().resetBestAntSoFar();
        }
        return iterationResult;
    }
}
