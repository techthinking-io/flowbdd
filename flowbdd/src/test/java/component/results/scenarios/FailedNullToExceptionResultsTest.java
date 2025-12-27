/*
 * Flow BDD - The most productive way to test.
 * Copyright (C) 2021-2025 James Bayliss.
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

package component.results.scenarios;

import component.results.AbstractResultsForTestSuite;
import io.techthinking.flowbdd.report.junit5.results.model.notes.Notes;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResult;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.FailedDueToExceptionTestCasesUnderTest;

import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultBuilder.aTestCaseResult;
import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.FAILED;
import static io.techthinking.flowbdd.report.junit5.results.model.TestSuiteTotalsBuilder.aTestSuiteResultsMetadata;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FailedNullToExceptionResultsTest extends AbstractResultsForTestSuite  {

    @BeforeAll
    public static void enableTest() {
        FailedDueToExceptionTestCasesUnderTest.setEnabled(true);
    }

    @AfterAll
    public static void disableTest() {
        FailedDueToExceptionTestCasesUnderTest.setEnabled(false);
    }

    @Override
    public Class<?> classUnderTest() {
        return FailedDueToExceptionTestCasesUnderTest.class;
    }

    @Test
    void verifyResultsForFailedDueToNullPointerTestCases() {
        assertThat(testSuiteResult().getTotals()).isEqualTo(
            aTestSuiteResultsMetadata()
                .withTestCaseCount(4)
                .withFailedCount(4)
                .build()
        );

        TestCaseResult testMethod = testCaseResult("testMethod");
        assertEqualsIgnoringCause(testMethod, aFailedTestMethodDueToException());
        assertNullPointerCause(testMethod);

        assertEqualsIgnoringCause(firstTestCaseResult("paramTest"),
            aFailedParamTestCaseResultDueToException()
                .withWordify("Method that throws a pointer method with value 1")
                .withArgs(singletonList("value 1"))
                .withName("paramTest")
                .withDisplayName("Param test value 1")
                .build()
        );
        assertNullPointerCause(firstTestCaseResult("paramTest"));

        assertEqualsIgnoringCause(secondTestCaseResult("paramTest"),
            aFailedParamTestCaseResultDueToException()
                .withWordify("Method that throws a pointer method with value 2")
                .withArgs(singletonList("value 2"))
                .withName("paramTest")
                .withDisplayName("Param test value 2")
                .build()
        );
        assertNullPointerCause(secondTestCaseResult("paramTest"));

        assertEqualsIgnoringCause(thirdTestCaseResult("paramTest"),
            aFailedParamTestCaseResultDueToException()
                .withWordify("Method that throws a pointer method with value 3")
                .withArgs(singletonList("value 3"))
                .withName("paramTest")
                .withDisplayName("Param test value 3")
                .build()
        );
        assertNullPointerCause(thirdTestCaseResult("paramTest"));
    }

    private void assertNullPointerCause(TestCaseResult result) {
        Throwable cause = result.getCause().orElseThrow(() -> new RuntimeException("Expected cause"));
        assertThat(cause.getMessage()).isNull();
        assertThat(cause.getClass()).isEqualTo(NullPointerException.class);
        assertThat(cause.getCause()).isNull();
        assertThat(cause.getStackTrace()).isNotNull();
    }

    private TestCaseResultBuilder aFailedParamTestCaseResultDueToException() {
        return aTestCaseResult()
            .withStatus(FAILED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes());
    }

    private TestCaseResult aFailedTestMethodDueToException() {
        return aTestCaseResult()
            .withName("testMethod")
            .withDisplayName("Test method")
            .withWordify("Method that throws a pointer method")
            .withStatus(FAILED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes())
            .build();
    }
}
