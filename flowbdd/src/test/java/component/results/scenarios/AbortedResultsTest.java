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

package component.results.scenarios;

import component.results.AbstractResultsForTestSuite;
import com.flowbdd.report.junit5.results.model.notes.Notes;
import com.flowbdd.report.junit5.results.model.TestCaseResult;
import com.flowbdd.report.junit5.results.model.TestCaseResultBuilder;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.AbortedTestCasesUnderTest;

import static com.flowbdd.report.junit5.results.model.TestCaseResultBuilder.aTestCaseResult;
import static com.flowbdd.report.junit5.results.model.TestCaseResultStatus.ABORTED;
import static com.flowbdd.report.junit5.results.model.TestSuiteTotalsBuilder.aTestSuiteResultsMetadata;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class AbortedResultsTest extends AbstractResultsForTestSuite {

    @Override
    public Class<?> classUnderTest() {
        return AbortedTestCasesUnderTest.class;
    }

    @Test
    void verifyResultsForAbortedTestCases() {
        assertThat(testSuiteResult().getTotals()).isEqualTo(
            aTestSuiteResultsMetadata()
                .withTestCaseCount(4)
                .withAbortedCount(4)
                .build());

        TestCaseResult testMethod = testCaseResult("testMethod");
        assertEqualsIgnoringCause(testMethod, anAbortedTestMethod());
        assertThat(testMethod).isEqualToIgnoringGivenFields(anAbortedTestMethod(), "cause");
        assertThat(testMethod.getCause()).isPresent();
        assertCauseWithMessage(testMethod, "Assumption failed: testMethod does not contain Z");

        assertEqualsIgnoringCause(firstTestCaseResult("paramTest"),
            anAbortedParamTestCaseResultDueToException()
                .withWordify("Aborting assertion with value 1")
                .withArgs(singletonList("value 1"))
                .withName("paramTest")
                .withDisplayName("Param test value 1")
                .build()
        );
        assertCauseWithMessage(firstTestCaseResult("paramTest"), "Assumption failed: value 1 does not contain z");

        assertEqualsIgnoringCause(secondTestCaseResult("paramTest"),
            anAbortedParamTestCaseResultDueToException()
                .withWordify("Aborting assertion with value 2")
                .withArgs(singletonList("value 2"))
                .withName("paramTest")
                .withDisplayName("Param test value 2")
                .build()
        );
        assertCauseWithMessage(secondTestCaseResult("paramTest"), "Assumption failed: value 2 does not contain z");

        assertEqualsIgnoringCause(thirdTestCaseResult("paramTest"),
            anAbortedParamTestCaseResultDueToException()
                .withWordify("Aborting assertion with value 3")
                .withArgs(singletonList("value 3"))
                .withName("paramTest")
                .withDisplayName("Param test value 3")
                .build()
        );
        assertCauseWithMessage(thirdTestCaseResult("paramTest"), "Assumption failed: value 3 does not contain z");
    }

    private TestCaseResultBuilder anAbortedParamTestCaseResultDueToException() {
        return aTestCaseResult()
            .withName("paramTest")
            .withDisplayName("param test")
            .withStatus(ABORTED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes());
    }

    private TestCaseResult anAbortedTestMethod() {
        return aTestCaseResult()
            .withName("testMethod")
            .withDisplayName("Test method")
            .withWordify("Aborting assertion")
            .withStatus(ABORTED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes())
            .build();
    }
}
