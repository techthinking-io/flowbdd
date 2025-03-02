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

package io.techthinking.flowbdd.report.report.adapter;

import io.techthinking.flowbdd.report.report.model.TestSuite;
import io.techthinking.flowbdd.report.report.model.TestSuiteLinks;
import io.techthinking.flowbdd.report.report.model.TestSuiteNameToFile;
import io.techthinking.flowbdd.report.report.writers.FileNameProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReportTestSuiteLinksFactory {
    private final FileNameProvider fileNameProvider;

    public ReportTestSuiteLinksFactory(FileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    public TestSuiteLinks create(List<TestSuite> testSuites) {
        return new TestSuiteLinks(testSuites.stream().map(this::testSuiteNameToFile).collect(toList()));
    }

    private TestSuiteNameToFile testSuiteNameToFile(TestSuite testSuite) {
        return new TestSuiteNameToFile(testSuite.getName(), fileNameProvider.file(testSuite).getFileName().toString());
    }
}
