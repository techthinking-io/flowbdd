/*
 * Flow BDD - The productive way to test.
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

public class TestSuiteTimings {
    private final long beforeAll;
    private final long afterAll;

    @JsonCreator
    public TestSuiteTimings(
        @JsonProperty("beforeAll") long beforeAll,
        @JsonProperty("afterAll") long afterAll) {
        this.beforeAll = beforeAll;
        this.afterAll = afterAll;
    }

    public long getBeforeAll() {
        return beforeAll;
    }

    public long getAfterAll() {
        return afterAll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSuiteTimings)) return false;
        TestSuiteTimings that = (TestSuiteTimings) o;
        return beforeAll == that.beforeAll && afterAll == that.afterAll;
    }

    @Override
    public int hashCode() {
        return Objects.hash(beforeAll, afterAll);
    }

    @Override
    public String toString() {
        return "TestSuiteTimings{" +
            "beforeAll=" + beforeAll +
            ", afterAll=" + afterAll +
            '}';
    }
}
