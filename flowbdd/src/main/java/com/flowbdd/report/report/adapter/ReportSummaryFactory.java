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

package com.flowbdd.report.report.adapter;

import com.flowbdd.report.report.model.TestSuite;
import com.flowbdd.report.report.model.TestSuiteSummary;

import java.util.List;

/**
 * private Datetime timestamp="2021-03-30T20:03:44"
 * private String hostname="Jamess-MacBook-Pro.local"
 * privat long time="0.021"
 */
public class ReportSummaryFactory {
    public static TestSuiteSummary create(List<TestSuite> testSuites) {
        ReportSummaryBuilder builder = new ReportSummaryBuilder();
        testSuites.forEach(testSuite -> builder.increment(testSuite.getSummary()));
        return builder.build();
    }

    static class ReportSummaryBuilder {
        private int testCount;
        private int passedCount;
        private int skippedCount;
        private int failedCount;
        private int abortedCount;

        public void increment(TestSuiteSummary summary) {
            testCount += summary.getTests();
            passedCount += summary.getPassed();
            skippedCount += summary.getSkipped();
            failedCount += summary.getFailed();
            abortedCount += summary.getAborted();
        }

        private TestSuiteSummary build() {
            return new TestSuiteSummary(
                testCount,
                passedCount,
                skippedCount,
                failedCount,
                abortedCount);
        }
    }
}
