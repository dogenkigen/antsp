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

package com.mlaskows.antsp.datamodel;

import java.util.List;

/**
 *
 */
public class IterationResult {
    private final List<Ant> sortedAnts;
    private final Ant bestAntSoFar;
    private final int iterationsWithNoImprovement;

    public IterationResult(List<Ant> sortedAnts, Ant bestAntSoFar, int iterationsWithNoImprovement) {
        this.sortedAnts = sortedAnts;
        this.bestAntSoFar = bestAntSoFar;
        this.iterationsWithNoImprovement = iterationsWithNoImprovement;
    }

    public List<Ant> getSortedAnts() {
        return sortedAnts;
    }

    public Ant getIterationBestAnt() {
        return sortedAnts.get(0);
    }

    public Ant getBestAntSoFar() {
        return bestAntSoFar;
    }

    public int getIterationsWithNoImprovement() {
        return iterationsWithNoImprovement;
    }
}
