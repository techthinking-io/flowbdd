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

package com.flowbdd.report.report.model.builders;

import com.flowbdd.report.report.model.TestSuite;
import com.flowbdd.report.report.model.notes.Notes;
import io.flowbdd.bdd.report.utils.Builder;
import io.flowbdd.bdd.report.utils.BuilderUtils;

import java.util.ArrayList;
import java.util.List;

import static com.flowbdd.report.report.model.builders.TestSuiteSummaryBuilder.aTestSuiteSummary;

public final class TestSuiteBuilder implements Builder<TestSuite> {
    private String title;
    private String name;
    private String className;
    private String packageName;
    private final List<TestCaseBuilder> testCases = new ArrayList<>();;
    private TestSuiteSummaryBuilder summary = aTestSuiteSummary();
    private Notes notes;

    private TestSuiteBuilder() {
    }

    public static TestSuiteBuilder aTestSuite() {
        return new TestSuiteBuilder();
    }

    public TestSuiteBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TestSuiteBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TestSuiteBuilder withClassName(String className) {
        this.className = className;
        return this;
    }

    public TestSuiteBuilder withPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public TestSuiteBuilder withTestCases(List<TestCaseBuilder> testCases) {
        this.testCases.clear();
        this.testCases.addAll(testCases);
        return this;
    }

    public TestSuiteBuilder withSummary(TestSuiteSummaryBuilder summary) {
        this.summary = summary;
        return this;
    }

    public TestSuiteBuilder withNotes(Notes notes) {
        this.notes = notes;
        return this;
    }

    public TestSuite build() {
        return new TestSuite(title, name, className, packageName, BuilderUtils.build(testCases), summary.build(), notes);
    }
}
