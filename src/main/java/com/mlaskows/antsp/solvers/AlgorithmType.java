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

import java.util.EnumSet;

public enum AlgorithmType {

    ANT_SYSTEM("Ant System"),
    MIN_MAX("MAX-MIN Ant System"),
    RANK_BASED("Rank Based Ant System"),
    ELITIST("Elitist Ant System");

    private static final EnumSet<AlgorithmType> antBased =
            EnumSet.of(ANT_SYSTEM, MIN_MAX, RANK_BASED, ELITIST);

    private final String text;

    AlgorithmType(final String text) {
        this.text = text;
    }

    public boolean isAntBased() {
        return antBased.contains(this);
    }

    @Override

    public String toString() {
        return text;
    }
}
