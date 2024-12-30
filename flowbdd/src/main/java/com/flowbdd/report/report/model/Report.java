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

package com.flowbdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * This is not marshalled, it's a holder for infrastructure/json
 * Used by data and html
 * As and when the project gets more mature this has to be refactored
 */
public class Report {
    private final DataReportIndex dataReportIndex;
    private final List<TestCase> testCases;
    private final List<TestSuite> testSuites;
    private final String timeStamp;

    @JsonCreator
    public Report(
        @JsonProperty("dataReportIndex") DataReportIndex dataReportIndex,
        @JsonProperty("testCases") List<TestCase> testCases,
        @JsonProperty("testSuites") List<TestSuite> testSuites,
        @JsonProperty("timeStamp") String timeStamp) {
        this.dataReportIndex = dataReportIndex;
        this.testCases = testCases;
        this.testSuites = testSuites;
        this.timeStamp = timeStamp;
    }

    public DataReportIndex getIndex() {
        return dataReportIndex;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public List<TestSuite> getTestSuites() {
        return testSuites;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return Objects.equals(dataReportIndex, report.dataReportIndex) && Objects.equals(testCases, report.testCases) && Objects.equals(testSuites, report.testSuites) && Objects.equals(timeStamp, report.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataReportIndex, testCases, testSuites, timeStamp);
    }

    @Override
    public String toString() {
        return "Report{" +
            "dataReportIndex=" + dataReportIndex +
            ", testCases=" + testCases +
            ", testSuites=" + testSuites +
            ", timeStamp=" + timeStamp +
            '}';
    }
}
