/*
 * Smart BDD - The smart way to do behavior-driven development.
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

package io.techthinking.flowbdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TestSuiteNameToFile {
    private final String name;
    private final String file;

    @JsonCreator
    public TestSuiteNameToFile(
        @JsonProperty("name") String name,
        @JsonProperty("file") String file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSuiteNameToFile)) return false;
        TestSuiteNameToFile that = (TestSuiteNameToFile) o;
        return Objects.equals(name, that.name) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, file);
    }

    @Override
    public String toString() {
        return "TestSuiteNameToFile{" +
            "name='" + name + '\'' +
            ", file='" + file + '\'' +
            '}';
    }
}
