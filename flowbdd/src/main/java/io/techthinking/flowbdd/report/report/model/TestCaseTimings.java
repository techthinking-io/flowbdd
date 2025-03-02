/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2022  James Bayliss
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

public class TestCaseTimings {
    private final long beforeEach;
    private final long afterEach;
    private final long underTest;

    /** beforeEach + underTest + afterEach */
    private final long total;

    @JsonCreator
    public TestCaseTimings(
        @JsonProperty("beforeEach") long beforeEach,
        @JsonProperty("afterEach") long afterEach,
        @JsonProperty("underTest") long underTest,
        @JsonProperty("total") long total) {
        this.beforeEach = beforeEach;
        this.afterEach = afterEach;
        this.underTest = underTest;
        this.total = total;
    }

    public long getBeforeEach() {
        return beforeEach;
    }

    public long getAfterEach() {
        return afterEach;
    }

    public long getUnderTest() {
        return underTest;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCaseTimings)) return false;
        TestCaseTimings that = (TestCaseTimings) o;
        return beforeEach == that.beforeEach && afterEach == that.afterEach && underTest == that.underTest && total == that.total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(beforeEach, afterEach, underTest, total);
    }

    @Override
    public String toString() {
        return "TestCaseTimings{" +
            "beforeEach=" + beforeEach +
            ", afterEach=" + afterEach +
            ", underTest=" + underTest +
            ", total=" + total +
            '}';
    }
}
