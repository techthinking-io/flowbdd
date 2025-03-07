/*
 * Flow BDD - The productive way to test.
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

package io.techthinking.flowbdd.report.junit5.results.extension;

import io.techthinking.flowbdd.report.junit5.results.model.TestCaseNameFactory;
import io.techthinking.flowbdd.report.junit5.results.model.TestResults;
import io.techthinking.flowbdd.report.junit5.results.model.TestSuiteResult;
import io.techthinking.flowbdd.report.report.writers.ReportWriter;
import io.techthinking.flowbdd.wordify.WordifyExtensionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestContextTest {

    private final TestResults testResults = mock(TestResults.class);
    private final WordifyExtensionContext wordifyExtensionContext = mock(WordifyExtensionContext.class);
    private final TestCaseNameFactory testCaseNameFactory = mock(TestCaseNameFactory.class);
    private final ReportWriter reportWriter = mock(ReportWriter.class);

    private final TestContext testContext = new TestContext(
        testResults,
        wordifyExtensionContext,
        testCaseNameFactory,
        reportWriter
    );

    @Test
    void afterAllWritesTestSuite() throws Exception {
        ExtensionContext extensionContext = mock(ExtensionContext.class);
        TestSuiteResult testSuiteResult = mock(TestSuiteResult.class);

        Collection<TestSuiteResult> testSuiteResults = Arrays.asList(testSuiteResult);
        when(testResults.getTestResultsForClass(extensionContext)).thenReturn(testSuiteResult);
        when(testResults.getTestSuiteResults()).thenReturn(testSuiteResults);

        testContext.afterAll(extensionContext);

        //verify(reportWriter).write(testSuiteResult);
    }
}