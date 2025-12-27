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

import java.util.List;

class TestSuiteTotalsFactory {
    public static TestSuiteTotals create(List<TestCaseResult> testCaseResults) {
        TestSuiteResultsMetadataBuilder builder = new TestSuiteResultsMetadataBuilder();
        testCaseResults.forEach(testSuite -> builder.increment(testSuite.getStatus()));
        return builder.build();
    }

    static class TestSuiteResultsMetadataBuilder {
        private int testCount;
        private int passedCount;
        private int skippedCount;
        private int failedCount;
        private int abortedCount;

        public void increment(TestCaseResultStatus status) {
            testCount++;
            switch (status) {
                case PASSED:
                    passedCount++;
                    break;
                case DISABLED:
                    skippedCount++;
                    break;
                case FAILED:
                    failedCount++;
                    break;
                case ABORTED:
                    abortedCount++;
                    break;
            }
        }

        private TestSuiteTotals build() {
            return new TestSuiteTotals(
                testCount,
                passedCount,
                skippedCount,
                failedCount,
                abortedCount);
        }
    }
}
