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

package com.mlaskows.antsp.statistics;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by maciej_laskowski on 13.07.2017.
 */
public class StatisticsBuilder {

    private final Map<Integer, Integer> iterationTourLength = new TreeMap<>();

    public StatisticsBuilder addIterationTourLength(int index, int tourLength) {
        iterationTourLength.put(index, tourLength);
        return this;
    }

    public StatisticsBuilder addIterationTourLength(int tourLength) {
        return addIterationTourLength(iterationTourLength.size(), tourLength);
    }

    public Statistics build() {
        return new Statistics(new TreeMap<>(iterationTourLength));
    }

}
