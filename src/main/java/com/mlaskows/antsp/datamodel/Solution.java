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

import com.mlaskows.antsp.statistics.Statistics;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by mlaskows on 24/06/2017.
 */
public class Solution {

    private final List<Integer> tour;
    private final int tourLength;
    private final Optional<Statistics> statistics;

    public Solution(List<Integer> tour, int tourLength) {
        this(tour, tourLength, Optional.empty());
    }

    public Solution(List<Integer> tour, int tourLength,
                    Optional<Statistics> statistics) {
        this.tour = tour;
        this.tourLength = tourLength;
        this.statistics = statistics;
    }

    public List<Integer> getTour() {
        return Collections.unmodifiableList(tour);
    }

    public int getTourLength() {
        return tourLength;
    }

    public Optional<Statistics> getStatistics() {
        return statistics;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "tourLength=" + tourLength +
                ", tour=" + tour + '}';
    }
}
