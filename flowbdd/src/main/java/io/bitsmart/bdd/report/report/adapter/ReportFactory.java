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

package io.bitsmart.bdd.report.report.adapter;

import io.bitsmart.bdd.report.junit5.results.model.TestCaseResult;
import io.bitsmart.bdd.report.junit5.results.model.TestCaseResultStatus;
import io.bitsmart.bdd.report.junit5.results.model.TestResults;
import io.bitsmart.bdd.report.junit5.results.model.TestSuiteClass;
import io.bitsmart.bdd.report.junit5.results.model.TestSuiteResult;
import io.bitsmart.bdd.report.junit5.results.model.TestSuiteTotals;
import io.bitsmart.bdd.report.junit5.results.model.notes.Notes;
import io.bitsmart.bdd.report.mermaid.SequenceDiagram;
import io.bitsmart.bdd.report.report.model.Argument;
import io.bitsmart.bdd.report.report.model.Clazz;
import io.bitsmart.bdd.report.report.model.DataReportIndex;
import io.bitsmart.bdd.report.report.model.Method;
import io.bitsmart.bdd.report.report.model.Report;
import io.bitsmart.bdd.report.report.model.Status;
import io.bitsmart.bdd.report.report.model.TestCaseTimings;
import io.bitsmart.bdd.report.report.model.TestSuiteSummary;
import io.bitsmart.bdd.report.report.model.VersionInfo;
import io.bitsmart.bdd.report.report.model.Throwable;
import io.bitsmart.bdd.report.report.writers.DataFileNameProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReportFactory {
    private static final ReportTestSuiteLinksFactory dataTestSuiteLinksFactory = new ReportTestSuiteLinksFactory(new DataFileNameProvider());

    public static Report create(TestResults testResults, VersionInfo versionInfo) {
        Collection<TestSuiteResult> testSuiteResults = testResults.getTestSuiteResults();
        List<io.bitsmart.bdd.report.report.model.TestSuite> testSuites = testSuites(testSuiteResults); // TODO add TestVersionInfo?? or just the web??
        List<io.bitsmart.bdd.report.report.model.TestCase> testCases = testCases(testSuiteResults);    // TODO add TestVersionInfo??
        DataReportIndex dataReportIndex = new DataReportIndex(
            dataTestSuiteLinksFactory.create(testSuites),
            ReportSummaryFactory.create(testSuites),
            versionInfo.getDateTimeAsString());
        return new Report(dataReportIndex, testCases, testSuites,  versionInfo.getDateTimeAsString());
    }

    private static List<io.bitsmart.bdd.report.report.model.TestCase> testCases(Collection<TestSuiteResult> testSuiteResults) {
        return testSuiteResults.stream()
            .flatMap(testSuite -> testSuite.getTestCaseResults().stream())
            .map(ReportFactory::testCase)
            .collect(toList());
    }

    private static List<io.bitsmart.bdd.report.report.model.TestSuite> testSuites(Collection<TestSuiteResult> testSuiteResults) {
        return testSuiteResults.stream().map(ReportFactory::testSuite).collect(toList());
    }

    public static io.bitsmart.bdd.report.report.model.TestSuite testSuite(TestSuiteResult testSuiteResult) {
        return new io.bitsmart.bdd.report.report.model.TestSuite(
            testSuiteResult.getTitle(),
            testSuiteResult.getTestSuiteClass().getFullyQualifiedName(),
            testSuiteResult.getTestSuiteClass().getClassName(),
            testSuiteResult.getTestSuiteClass().getPackageName(),
            testResults(testSuiteResult.getTestCaseResults()),
            testSuiteSummary(testSuiteResult.getTotals()),
            notes(testSuiteResult.getNotes()));
    }

    private static TestSuiteSummary testSuiteSummary(TestSuiteTotals metadata) {
        return new TestSuiteSummary(
            metadata.getTestCaseCount(),
            metadata.getPassedCount(),
            metadata.getAbortedCount(),
            metadata.getFailedCount(),
            metadata.getAbortedCount());
    }

    private static List<io.bitsmart.bdd.report.report.model.TestCase> testResults(List<TestCaseResult> testCaseResults) {
        return testCaseResults.stream().map(ReportFactory::testCase).collect(toList());
    }

    private static io.bitsmart.bdd.report.report.model.TestCase testCase(TestCaseResult testCaseResult) {
        return new io.bitsmart.bdd.report.report.model.TestCase(
            testCaseResult.getWordify(),
            statusFrom(testCaseResult.getStatus()),
            testCaseResult.getCause().map(ReportFactory::throwableParent).orElse(null),
            method(testCaseResult),
            clazz(testCaseResult.getTestSuiteClass()),
            notes(testCaseResult.getNotes()),
            new TestCaseTimings(0L, 0L, 0L, 0L));
    }

    private static Method method(TestCaseResult testCaseResult) {
        return new Method(testCaseResult.getName(), testCaseResult.getDisplayName(), arguments(testCaseResult.getArgs()));
    }

    private static List<Argument> arguments(List<Object> args) {
        List<Argument> argResults = new ArrayList<>();
        for (Object arg: args) {
            argResults.add(arg == null ? null : new Argument(clazz(arg.getClass()), arg.toString()));
        }
        return argResults;
    }

    private static Throwable throwableParent(java.lang.Throwable throwable) {
        if (throwable == null) return null;
        return new Throwable(
            clazz(throwable.getClass()),
            throwable.getMessage(),
            throwableChild(throwable),
            Arrays.asList("TODO stacktrace"));
    }

    private static Throwable throwableChild(java.lang.Throwable throwable) {
        if (throwable == null) return null;
        return new Throwable(
            clazz(throwable.getClass()),
            throwable.getMessage(),
            null,
            Arrays.asList("TODO stacktrace"));
    }

    private static Clazz clazz(TestSuiteClass testSuiteClass) {
        return new Clazz(
            testSuiteClass.getFullyQualifiedName(),
            testSuiteClass.getClassName(),
            testSuiteClass.getPackageName());
    }

    private static Clazz clazz(Class<?> clazz) {
        return new Clazz(clazz.getName(), clazz.getSimpleName(), clazz.getPackage().getName());
    }

    private static io.bitsmart.bdd.report.report.model.notes.Notes notes(Notes notes) {
        if (notes.text().getNotes().size() == 0 && notes.diagrams().getAll().size() ==  0) {
            return null;
        }

        List<SequenceDiagram> diagrams = new ArrayList<>(notes.diagrams().getAll().values());
        List<String> generatedDiagrams = diagrams.stream().map(SequenceDiagram::generate).collect(toList());
        return new io.bitsmart.bdd.report.report.model.notes.Notes(notes.text().getNotes(), generatedDiagrams);
    }

    private static Status statusFrom(TestCaseResultStatus status) {
        switch (status) {
            case PASSED:
                return Status.PASSED;
            default:
                return Status.FAILED;
        }
    }
}
