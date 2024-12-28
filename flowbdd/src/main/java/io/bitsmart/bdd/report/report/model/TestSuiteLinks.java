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

package io.bitsmart.bdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class TestSuiteLinks {
    private final List<TestSuiteNameToFile> testSuites;

    @JsonCreator
    public TestSuiteLinks(@JsonProperty("testSuites") List<TestSuiteNameToFile> testSuites) {
        this.testSuites = testSuites;
    }

    public List<TestSuiteNameToFile> getTestSuites() {
        return testSuites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSuiteLinks)) return false;
        TestSuiteLinks that = (TestSuiteLinks) o;
        return Objects.equals(testSuites, that.testSuites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testSuites);
    }

    @Override
    public String toString() {
        return "TestSuiteLinks{" +
            "testSuites=" + testSuites +
            '}';
    }
}
