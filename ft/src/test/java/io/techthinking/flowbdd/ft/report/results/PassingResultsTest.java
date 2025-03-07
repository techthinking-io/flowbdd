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

package io.techthinking.flowbdd.ft.report.results;
import io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest;
import io.techthinking.flowbdd.report.report.model.Status;
import io.techthinking.flowbdd.report.report.model.TestCase;
import io.techthinking.flowbdd.report.report.model.builders.ArgumentBuilder;
import io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder;
import org.junit.jupiter.api.Test;

import static io.techthinking.flowbdd.report.report.model.builders.ArgumentBuilder.anArgument;
import static io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder.aClazz;
import static io.techthinking.flowbdd.report.report.model.builders.MethodBuilder.aMethod;
import static io.techthinking.flowbdd.report.report.model.builders.TestCaseBuilder.aTestCase;
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
public class PassingResultsTest extends AbstractResultsForData {

    @Override
    public Class<?> classUnderTest() {
        return ClassUnderTest.class;
    }

    /**
     * <pre>
     * {
     *   "links" : {
     *     "testSuites" : [ {
     *       "name" : "io.techthinking.flowbdd.ft.ClassUnderTest",
     *       "file" : "TEST-io.techthinking.flowbdd.ft.ClassUnderTest.json"
     *     } ]
     *   },
     *   "summary" : {
     *     "passed" : 4,
     *     "skipped" : 0,
     *     "failed" : 0,
     *     "aborted" : 0,
     *     "tests" : 4
     *   }
     * }
     * </pre>
     */
    @Test
    void generatesIndexJson() {
        assertIndexLinks(aTestSuiteNameToFile()
            .withName("io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest")
            .withFile("TEST-io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest.json"));
        assertIndexSummary(aTestSuiteSummary()
            .withTestCase(6)
            .withPassed(6)
            .withSkipped(0)
            .withFailed(0)
            .withAborted(0));
        assertIndexTimeStamp();
    }

    @Test
    void verifyResultsForPassingTestCases() {
        assertTestSuitClass(testSuiteResult(), classUnderTest());
        assertThat(testCaseResult("testMethod")).isEqualTo(passingTestCase());
        assertThat(firstTestCaseResult("paramTest")).isEqualTo(passingParamTestCase("1"));
        assertThat(secondTestCaseResult("paramTest")).isEqualTo(passingParamTestCase("2"));
        assertThat(thirdTestCaseResult("paramTest")).isEqualTo(passingParamTestCase("3"));
    }

    public static TestCase passingTestCase() {
        return aTestCase()
            .withWordify("Passing assertion")
            .withStatus(Status.PASSED)
            .withMethod(aMethod().withName("testMethod").withWordify("Test method"))
            .withClazz(aDefaultClazz())
            .build();
    }

    public static TestCase passingParamTestCase(String number) {
        return aTestCase()
            .withWordify("Passing assertion with one param value " + number)
            .withStatus(Status.PASSED)
            .withMethod(aMethod().withName("paramTest").withWordify("Param test value " + number).withArgument(aStringArgument("value " + number)))
            .withClazz(aDefaultClazz())
            .build();
    }

    private static ArgumentBuilder aStringArgument(String value) {
        return anArgument().withClazz(aStringClazz()).withValue(value);
    }

    private static ClazzBuilder aStringClazz() {
        return aClazz().withClassName("String").withPackageName("java.lang").withFullyQualifiedName("java.lang.String");
    }

    private static ClazzBuilder aDefaultClazz() {
        return aClazz().withClassName("ClassUnderTest").withPackageName("io.techthinking.flowbdd.ft.undertest.basic").withFullyQualifiedName("io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest");
    }
}
