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

package io.techthinking.flowbdd.report.report.model.builders;

import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.bdd.report.utils.Builder;
import io.techthinking.flowbdd.bdd.report.utils.BuilderUtils;

import java.util.ArrayList;
import java.util.List;

import static io.techthinking.flowbdd.report.report.model.builders.ReportIndexBuilder.*;

public final class ReportBuilder implements Builder<Report> {
    private ReportIndexBuilder reportIndex = aReportIndex();
    final private List<TestCaseBuilder> testCases = new ArrayList<>();
    final private List<TestSuiteBuilder> testSuites = new ArrayList<>();
    private String timeStamp;

    private ReportBuilder() {
    }

    public static ReportBuilder aReport() {
        return new ReportBuilder();
    }

    public ReportBuilder withReportIndex(ReportIndexBuilder reportIndex) {
        this.reportIndex = reportIndex;
        return this;
    }

    public ReportBuilder withTestCases(List<TestCaseBuilder> testCases) {
        this.testCases.clear();
        this.testCases.addAll(testCases);
        return this;
    }

    public ReportBuilder withTestSuites(List<TestSuiteBuilder> testSuites) {
        this.testSuites.clear();
        this.testSuites.addAll(testSuites);
        return this;
    }

    public ReportBuilder withTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public Report build() {
        return new Report(reportIndex.build(), BuilderUtils.build(testCases), BuilderUtils.build(testSuites), timeStamp);
    }
}
