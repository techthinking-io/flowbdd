/*
 * Flow BDD - The most productive way to test.
 * Copyright (C) 2021-2025 James Bayliss.
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

import java.util.Objects;

public class Timings {
    private long beforeEach = 0;
    private long underTest = 0;
    private long afterEach = 0;

    public long getBeforeEach() {
        return beforeEach;
    }

    public Timings setBeforeEach(long beforeEach) {
        this.beforeEach = beforeEach;
        return this;
    }

    public long getUnderTest() {
        return underTest;
    }

    public Timings setUnderTest(long underTest) {
        this.underTest = underTest;
        return this;
    }

    public long getAfterEach() {
        return afterEach;
    }

    public Timings setAfterEach(long afterEach) {
        this.afterEach = afterEach;
        return this;
    }

    public long getTotal() {
        return beforeEach + underTest + afterEach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timings)) return false;
        Timings timings = (Timings) o;
        return beforeEach == timings.beforeEach && underTest == timings.underTest && afterEach == timings.afterEach;
    }

    @Override
    public int hashCode() {
        return Objects.hash(beforeEach, underTest, afterEach);
    }

    @Override
    public String toString() {
        return "Timings{" +
            "beforeEach=" + beforeEach +
            ", underTest=" + underTest +
            ", afterEach=" + afterEach +
            ", total=" + getTotal() +
            '}';
    }
}
