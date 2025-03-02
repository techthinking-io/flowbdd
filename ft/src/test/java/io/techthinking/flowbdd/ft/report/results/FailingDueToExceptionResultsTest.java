/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2023  James Bayliss
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

package io.techthinking.flowbdd.ft.report.results;
import io.techthinking.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest;
import io.techthinking.flowbdd.report.report.model.Status;
import io.techthinking.flowbdd.report.report.model.TestCase;
import io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder;
import org.junit.jupiter.api.Test;

import static io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder.aClazz;
import static io.techthinking.flowbdd.report.report.model.builders.MethodBuilder.aMethod;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteNameToFileBuilder.aTestSuiteNameToFile;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteSummaryBuilder.aTestSuiteSummary;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Notes:
 * Due to the static nature of SmartReport, this class can't be annotated with SmartReport.
 * <p>
 * TODO address the below
 * SmartTestExecutionListener will write the index and test suites files twice as it will also listen to this as a test.
 * There's no point adding logic to SmartTestExecutionListener as this is specific to how it is run here.
 * Else we could check a tag and or annotation maybe?
 */
public class FailingDueToExceptionResultsTest extends AbstractResultsForData {

    @Override
    public Class<?> classUnderTest() {
        return FailedDueToExceptionTestCasesUnderTest.class;
    }

    /**
     * <pre>
     * {
     *   "links" : {
     *     "testSuites" : [ {
     *       "name" : "com.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest",
     *       "file" : "TEST-com.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest.json"
     *     } ]
     *   },
     *   "summary" : {
     *     "passed" : 0,
     *     "skipped" : 0,
     *     "failed" : 4,
     *     "aborted" : 0,
     *     "tests" : 4
     *   },
     *   "timeStamp" : "2023-03-14T19:38:07.65Z"
     * }
     * </pre>
     */
    @Test
    void generatesIndexJson() {
        assertIndexLinks(aTestSuiteNameToFile()
            .withName("com.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest")
            .withFile("TEST-com.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest.json"));
        assertIndexSummary(aTestSuiteSummary()
            .withTestCase(4)
            .withPassed(0)
            .withSkipped(0)
            .withFailed(4)
            .withAborted(0));
        assertIndexTimeStamp();
    }

    @Test
    void verifyResultsForFailingDueToExceptionTestCases() {
        assertTestSuitClass(testSuiteResult(), classUnderTest());
        assertIsFailingTestCase(testCaseResult("testMethod"));
// TODO assertThat(testCaseResult("testMethod")).isEqualTo(failingTestCase());
// TODO assertThat(firstTestCaseResult("paramTest")).isEqualTo(failingParamTestCase("1"));
// TODO assertThat(secondTestCaseResult("paramTest")).isEqualTo(failingParamTestCase("2"));
// TODO assertThat(thirdTestCaseResult("paramTest")).isEqualTo(failingParamTestCase("3"));
    }

    /**
     * TestCase {
     * wordify='Method that throws a pointer method',
     * status=FAILED,
     * cause=Throwable{clazz=Clazz{fullyQualifiedName='java.lang.NullPointerException', className='NullPointerException', packageName='java.lang'}, message='null', cause=Throwable{clazz=Clazz{fullyQualifiedName='java.lang.NullPointerException', className='NullPointerException', packageName='java.lang'}, message='null', cause=null, stackTrace=[TODO stacktrace]}, stackTrace=[TODO stacktrace]},
     * method=Method{name='testMethod', wordify='Test method', arguments=[]},
     * clazz=Clazz{fullyQualifiedName='com.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest', className='FailedDueToExceptionTestCasesUnderTest', packageName='com.flowbdd.ft.undertest.basic'},
     * notes=null,
     * timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}
     * }
     */
    public void assertIsFailingTestCase(TestCase testCaseResult) {
        assertThat(testCaseResult.getWordify()).isEqualTo("Method that throws a pointer method");
        assertThat(testCaseResult.getStatus()).isEqualTo(Status.FAILED);
        assertThat(testCaseResult.getMethod()).isEqualTo(aMethod().withName("testMethod").withWordify("Test method").build());
        assertThat(testCaseResult.getClazz()).isEqualTo((aDefaultClazz().build()));
    }



    private static ClazzBuilder aDefaultClazz() {
        return aClazz().withClassName("FailedDueToExceptionTestCasesUnderTest").withPackageName("com.flowbdd.ft.undertest.basic").withFullyQualifiedName("com.flowbdd.ft.undertest.basic.FailedDueToExceptionTestCasesUnderTest");
    }
}
