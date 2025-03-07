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

package component.results.scenarios;

import component.results.AbstractResultsForTestSuite;
import io.techthinking.flowbdd.report.junit5.results.model.notes.Notes;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResult;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.FailedTestCasesUnderTest;

import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultBuilder.aTestCaseResult;
import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.FAILED;
import static io.techthinking.flowbdd.report.junit5.results.model.TestSuiteTotalsBuilder.aTestSuiteResultsMetadata;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FailedResultsTest extends AbstractResultsForTestSuite {

    @BeforeAll
    public static void enableTest() {
        FailedTestCasesUnderTest.setEnabled(true);
    }

    @AfterAll
    public static void disableTest() {
        FailedTestCasesUnderTest.setEnabled(false);
    }

    @Override
    public Class<?> classUnderTest() {
        return FailedTestCasesUnderTest.class;
    }

    @Test
    void verifyResultsForFailedTestCases() {
        assertThat(testSuiteResult().getTotals()).isEqualTo(
            aTestSuiteResultsMetadata()
                .withTestCaseCount(4)
                .withFailedCount(4)
                .build());

        TestCaseResult testMethod = testCaseResult("testMethod");
        assertEqualsIgnoringCause(testMethod, aFailedTestMethod());
        assertCauseWithMessage(testMethod, "\n" + "Expecting:\n" + " <true>\n" + "to be equal to:\n" + " <false>\n" + "but was not.");

        assertEqualsIgnoringCause(firstTestCaseResult("paramTest"),
            aFailedParamTestCaseResult()
                .withWordify("Failing assertion with value 1")
                .withArgs(singletonList("value 1"))
                .withName("paramTest")
                .withDisplayName("Param test value 1")
                .build()
        );
        assertCauseWithMessage(firstTestCaseResult("paramTest"), "\nExpecting:\n <\"value 1\">\nto be equal to:\n <null>\nbut was not.");

        assertEqualsIgnoringCause(secondTestCaseResult("paramTest"),
            aFailedParamTestCaseResult()
                .withWordify("Failing assertion with value 2")
                .withArgs(singletonList("value 2"))
                .withName("paramTest")
                .withDisplayName("Param test value 2")
                .build()
        );
        assertCauseWithMessage(secondTestCaseResult("paramTest"), "\nExpecting:\n <\"value 2\">\nto be equal to:\n <null>\nbut was not.");

        assertEqualsIgnoringCause(thirdTestCaseResult("paramTest"),
            aFailedParamTestCaseResult()
                .withWordify("Failing assertion with value 3")
                .withArgs(singletonList("value 3"))
                .withName("paramTest")
                .withDisplayName("Param test value 3")
                .build()
        );
        assertCauseWithMessage(thirdTestCaseResult("paramTest"), "\nExpecting:\n <\"value 3\">\nto be equal to:\n <null>\nbut was not.");
    }

    private TestCaseResultBuilder aFailedParamTestCaseResult() {
        return aTestCaseResult()
            .withStatus(FAILED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes());
    }

    private TestCaseResult aFailedTestMethod() {
        return aTestCaseResult()
            .withName("testMethod")
            .withDisplayName("Test method")
            .withWordify("Failing assertion")
            .withStatus(FAILED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes())
            .build();
    }
}
