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

package io.techthinking.flowbdd.report.junit5.results.model;

public final class TestSuiteTotalsBuilder {
    private int testCaseCount = 0;
    private int passedCount = 0;
    private int skippedCount = 0;
    private int failedCount = 0;
    private int abortedCount = 0;

    private TestSuiteTotalsBuilder() {
    }

    public static TestSuiteTotalsBuilder aTestSuiteResultsMetadata() {
        return new TestSuiteTotalsBuilder();
    }

    public TestSuiteTotalsBuilder withTestCaseCount(int testCaseCount) {
        this.testCaseCount = testCaseCount;
        return this;
    }

    public TestSuiteTotalsBuilder withPassedCount(int passedCount) {
        this.passedCount = passedCount;
        return this;
    }

    public TestSuiteTotalsBuilder withSkippedCount(int skippedCount) {
        this.skippedCount = skippedCount;
        return this;
    }

    public TestSuiteTotalsBuilder withFailedCount(int failedCount) {
        this.failedCount = failedCount;
        return this;
    }

    public TestSuiteTotalsBuilder withAbortedCount(int abortedCount) {
        this.abortedCount = abortedCount;
        return this;
    }

    public TestSuiteTotals build() {
        return new TestSuiteTotals(testCaseCount, passedCount, skippedCount, failedCount, abortedCount);
    }
}
