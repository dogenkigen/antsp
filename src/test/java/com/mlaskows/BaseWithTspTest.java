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

package com.mlaskows;

import com.mlaskows.tsplib.parser.TspLibParser;
import com.mlaskows.tsplib.datamodel.tour.Tour;
import com.mlaskows.tsplib.datamodel.tsp.Tsp;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by maciej_laskowski on 05.07.2017.
 */
public interface BaseWithTspTest {

    default Tsp getTsp(String fileName) throws IOException {
        return TspLibParser.parseTsp(getFileAbsolutePath(fileName));
    }

    default Tour getTour(String fileName) throws IOException {
        return TspLibParser.parseTour(getFileAbsolutePath(fileName));
    }

    default String getFileAbsolutePath(String fileName) {
        return Paths.get(fileName).toAbsolutePath().toString();
    }

}
