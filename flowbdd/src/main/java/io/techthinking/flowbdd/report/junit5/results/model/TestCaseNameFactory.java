/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2021  James Bayliss
 *
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.techthinking.flowbdd.report.junit5.results.model;

import java.util.List;
import java.util.stream.Collectors;

public class TestCaseNameFactory {
    public String createName(String name, List<Object> args) {
        if (args.isEmpty()) {
            return name;
        }
        return name + args.stream().map(arg -> arg == null ? "null" : arg.toString()).collect(Collectors.joining(", ", " ", ""));
    }
}
