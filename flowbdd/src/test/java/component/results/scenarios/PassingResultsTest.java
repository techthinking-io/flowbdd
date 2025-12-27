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
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResult;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultBuilder;
import io.techthinking.flowbdd.report.junit5.results.model.notes.Notes;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.ClassUnderTest;

import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultBuilder.aTestCaseResult;
import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.PASSED;
import static io.techthinking.flowbdd.report.junit5.results.model.TestSuiteTotalsBuilder.aTestSuiteResultsMetadata;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class PassingResultsTest extends AbstractResultsForTestSuite {

    @Override
    public Class<?> classUnderTest() {
        return ClassUnderTest.class;
    }

    @Test
    void verifyResultsForPassingTestCases() {
        assertTestSuitClass(testSuiteResult(), classUnderTest());
        assertThat(testSuiteResult().getTotals()).isEqualTo(
            aTestSuiteResultsMetadata()
                .withTestCaseCount(6)
                .withPassedCount(6)
                .build()
        );

        assertThat(testCaseResult("testMethod")).isEqualTo(aPassedTestCaseResult());

        assertThat(firstTestCaseResult("paramTest")).isEqualTo(
            aPassedParamTestCaseResult()
                .withWordify("Passing assertion with one param value 1")
                .withArgs(singletonList("value 1"))
                .withName("paramTest")
                .withDisplayName("Param test value 1")
                .build()
        );
        assertThat(secondTestCaseResult("paramTest")).isEqualTo(
            aPassedParamTestCaseResult()
                .withWordify("Passing assertion with one param value 2")
                .withArgs(singletonList("value 2"))
                .withName("paramTest")
                .withDisplayName("Param test value 2")
                .build()
        );
        assertThat(thirdTestCaseResult("paramTest")).isEqualTo(
            aPassedParamTestCaseResult()
                .withWordify("Passing assertion with one param value 3")
                .withArgs(singletonList("value 3"))
                .withName("paramTest")
                .withDisplayName("Param test value 3")
                .build()
        );
    }

    private TestCaseResultBuilder aPassedParamTestCaseResult() {
        return aTestCaseResult()
            .withStatus(PASSED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes());
    }

    private TestCaseResult aPassedTestCaseResult() {
        return aTestCaseResult()
            .withName("testMethod")
            .withDisplayName("Test method")
            .withWordify("Passing assertion")
            .withStatus(PASSED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes())
            .build();
    }
}
