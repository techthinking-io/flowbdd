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

package io.techthinking.flowbdd.report.junit5.results.model;

import io.techthinking.flowbdd.report.junit5.results.model.notes.Notes;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static io.techthinking.flowbdd.report.junit5.results.model.TestSuiteClass.*;

/**
 * <pre> {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <testsuite name="junit5.learning.parameters.LearningTest" tests="8" skipped="0" failures="0" errors="0" timestamp="2021-03-30T20:03:44" hostname="Jamess-MacBook-Pro.local" time="0.021">
 *   <properties/>
 *   <testcase name="[1] test, TEST" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="[2] tEst, TEST" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="[3] Java, JAVA" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="RepeatingTest 1/5" classname="junit5.learning.parameters.LearningTest" time="0.005"/>
 *   <testcase name="RepeatingTest 2/5" classname="junit5.learning.parameters.LearningTest" time="0.0"/>
 *   <testcase name="RepeatingTest 3/5" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="RepeatingTest 4/5" classname="junit5.learning.parameters.LearningTest" time="0.0"/>
 *   <testcase name="RepeatingTest 5/5" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <system-out><![CDATA[RepeatingTest 1/5-->1
 * RepeatingTest 2/5-->2
 * RepeatingTest 3/5-->3
 * RepeatingTest 4/5-->4
 * RepeatingTest 5/5-->5
 * ]]></system-out>
 *   <system-err><![CDATA[]]></system-err>
 * </testsuite>
 *
 * This could be a service to create an immutable TestSuiteResults??
 * }</pre>
 */
public class TestSuiteResult {
    private final TestSuiteClass testSuiteClass;
    /** all results including different params and or repeated test */
    private final List<TestCaseResult> testCaseResults = new ArrayList<>();
    private final ConcurrentHashMap<ExtensionContext, TestCaseResult> contextToTestCaseResult = new ConcurrentHashMap<>();
    private final String title;
    private final Notes notes;
    private TestSuiteTotals totals;

    public TestSuiteResult(TestSuiteClass testSuiteClass, String title, Notes notes) {
        this.testSuiteClass = testSuiteClass;
        this.title = title;
        this.notes = notes;
    }

    // /** Creates the testcase, before we have invocationContext */
    public TestCaseResult startTestCase(ExtensionContext context) {
        TestCaseResult testCaseResult = createTestCaseResult(context);
        this.testCaseResults.add(testCaseResult);
        contextToTestCaseResult.put(context, testCaseResult);
        return testCaseResult;
    }

    public void completeTestSuite() {
        totals = TestSuiteTotalsFactory.create(testCaseResults);
    }

    public TestCaseResult getTestCaseResult(ExtensionContext context) {
        return contextToTestCaseResult.get(context);
    }

    public String getTitle() {
        return title;
    }

    public TestSuiteClass getTestSuiteClass() {
        return testSuiteClass;
    }

    public TestSuiteTotals getTotals() {
        return totals;
    }

    public List<TestCaseResult> getTestCaseResults() {
        return testCaseResults;
    }

    public Notes getNotes() {
        return notes;
    }

    private TestCaseResult createTestCaseResult(ExtensionContext context) {
        return new TestCaseResult(testSuiteClass(context.getRequiredTestClass()), new Notes());
    }
}
