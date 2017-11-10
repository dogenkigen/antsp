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

package com.mlaskows.antsp.exeptions;

/**
 * Created by mlaskows on 05/07/2017.
 */
public enum Reason {

    NO_BEST_ANT("Couldn't find best ant to construct solution!"),
    EMPTY_HEURISTIC_MATRIX("Heuristic matrix can't be empty!"),
    EMPTY_NN_MATRIX("Nearest neighbors matrix can't be empty!"),
    NO_HEURISTIC_SOLUTION("Heuristic solution should be included in static data");

    private final String text;

    private Reason(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
