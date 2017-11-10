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

package com.mlaskows.antsp.solvers;

import com.mlaskows.BaseWithTspTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseOptSolverTest implements BaseWithTspTest {

    protected List<Integer> getInitialTour(int size) {
        return IntStream.concat(IntStream.range(0, size), IntStream.of(0))
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
    }

    protected int calculateDistance(int[][] australianDistances, List<Integer>
            initialTour) {
        int distance = 0;
        for (int i = 0; i < initialTour.size() - 1; i++) {
            distance += australianDistances[initialTour.get(i)][initialTour.get(i + 1)];
        }
        return distance;
    }

}
