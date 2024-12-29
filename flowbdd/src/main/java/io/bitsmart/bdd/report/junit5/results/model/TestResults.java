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

package io.bitsmart.bdd.report.junit5.results.model;

import io.bitsmart.bdd.report.junit5.results.model.notes.Notes;
import com.flowbdd.wordify.legacy.WordifyString;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static io.bitsmart.bdd.report.junit5.results.model.TestSuiteClass.testSuiteClass;

public class TestResults {

    /**
     * Performance improvement.
     * usage:
     * <p>
     * - For each test suite
     * -- write TestSuite
     * -- updateTestSuiteResultsMetadata
     * -- removeTestSuite
     * - After all tests are complete, create report index from all the TestSuiteResultsMetadata
     * <p>
     * private final ConcurrentHashMap<TestSuiteClass, TestSuiteResultsMetadata> testSuiteToTestSuiteResultsMetadata = new ConcurrentHashMap<>();
     * <p>
     * public void removeTestSuite(TestSuiteClass testSuiteClass) {
     * testSuiteToTestSuiteResults.remove(testSuiteClass);
     * }
     * <p>
     * public void updateTestSuiteResultsMetadata(TestSuiteClass testSuiteClass) {
     * testSuiteToTestSuiteResultsMetadata.put(testSuiteClass, testSuiteToTestSuiteResults.get(testSuiteClass).getMetadata());
     * }
     */

    private final ConcurrentHashMap<TestSuiteClass, TestSuiteResult> testSuiteToTestSuiteResults = new ConcurrentHashMap<>();

    public List<TestSuiteClass> getClasses() {
        return Collections.list(testSuiteToTestSuiteResults.keys());
    }

    public Collection<TestSuiteResult> getTestSuiteResults() {
        return testSuiteToTestSuiteResults.values();
    }

    public TestSuiteResult getTestSuiteResults(TestSuiteClass testSuiteClass) {
        return testSuiteToTestSuiteResults.get(testSuiteClass);
    }

    public TestSuiteResult getTestResultsForClass(ExtensionContext extensionContext) {
        return testSuiteToTestSuiteResults.get(getTestSuiteClass(extensionContext));
    }

    public TestSuiteResult startTestSuite(ExtensionContext context) {
        TestSuiteClass testSuiteClass = testSuiteClass(context.getRequiredTestClass());
        String title = new WordifyString(testSuiteClass.getClassName()).wordify(); //TODO
        TestSuiteResult testSuiteResult = new TestSuiteResult(testSuiteClass, title, new Notes());
        testSuiteToTestSuiteResults.put(getTestSuiteClass(context), testSuiteResult);
        return testSuiteResult;
    }

    public TestSuiteClass getTestSuiteClass(ExtensionContext extensionContext) {
        return testSuiteClass(extensionContext.getRequiredTestClass());
    }

    public void reset() {
        testSuiteToTestSuiteResults.clear();
    }
}
