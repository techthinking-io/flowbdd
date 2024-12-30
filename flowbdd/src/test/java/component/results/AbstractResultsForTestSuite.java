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

package component.results;

import com.flowbdd.report.junit5.launcher.TestLauncher;
import com.flowbdd.report.junit5.results.extension.SmartReport;
import com.flowbdd.report.junit5.results.model.TestCaseResult;
import com.flowbdd.report.junit5.results.model.TestSuiteClass;
import com.flowbdd.report.junit5.results.model.TestSuiteResult;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractResultsForTestSuite {
    private TestSuiteResult testSuiteResult;

    @BeforeEach
    void setupEachTest() {
        SmartReport.getTestContext().reset();
        TestLauncher.launch(classUnderTest());
        testSuiteResult = testSuiteResult(classUnderTest());
    }

    public abstract Class<?> classUnderTest();

    public TestSuiteClass testSuiteClass() {
        return TestSuiteClass.testSuiteClass(classUnderTest());
    }

    public TestCaseResult testCaseResult(int index) {
        return testSuiteResult.getTestCaseResults().get(index);
    }

    public TestCaseResult testCaseResult(String methodName) {
        return testSuiteResult().getTestCaseResults().stream()
            .findAny()
            .filter(testCase -> testCase.getName().equals(methodName))
            .orElse(null);
    }

    public List<TestCaseResult> testCaseResults(String methodName) {
        return testSuiteResult().getTestCaseResults().stream()
            .filter(testCase -> testCase.getName().equals(methodName))
            .collect(toList());
    }

    public TestCaseResult firstTestCaseResult(String methodName) {
        return testCaseResults(methodName).get(0);
    }

    public TestCaseResult secondTestCaseResult(String methodName) {
        return testCaseResults(methodName).get(1);
    }

    public TestCaseResult thirdTestCaseResult(String methodName) {
        return testCaseResults(methodName).get(2);
    }

    public TestSuiteResult testSuiteResult() {
        return testSuiteResult;
    }

    private TestSuiteResult testSuiteResult(Class<?> clazz) {
        return SmartReport.getTestContext().getTestResults().getTestSuiteResults(TestSuiteClass.testSuiteClass(clazz));
    }

    protected void assertTestSuitClass(TestSuiteResult testSuiteResult, Class<?> clazz) {
        assertThat(testSuiteResult.getTestSuiteClass().getClassName()).isEqualTo(clazz.getSimpleName());
        assertThat(testSuiteResult.getTestSuiteClass().getFullyQualifiedName()).isEqualTo(clazz.getPackage().getName() + "." + clazz.getSimpleName());
        assertThat(testSuiteResult.getTestSuiteClass().getPackageName()).isEqualTo(clazz.getPackage().getName());
    }

    protected void assertCauseWithMessage(TestCaseResult result, String message) {
        Throwable cause = result.getCause().orElseThrow(() -> new RuntimeException("Expected cause"));
        assertThat(cause.getMessage()).isEqualTo(message);
        assertThat(cause.getClass()).isNotNull();
        assertThat(cause.getCause()).isNull();
        assertThat(cause.getStackTrace()).isNotNull();
    }

    protected void assertEqualsIgnoringCause(TestCaseResult actual, TestCaseResult expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "cause");
    }
}
