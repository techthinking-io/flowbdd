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

package io.techthinking.flowbdd.report.report.model;

import io.techthinking.flowbdd.report.report.model.notes.Notes;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteBuilder.aTestSuite;
import static io.techthinking.flowbdd.report.report.model.builders.TestCaseBuilder.aTestCase;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteSummaryBuilder.aTestSuiteSummary;
import static org.assertj.core.api.Assertions.assertThat;

class TestSuiteMarkdownFactoryTest {

    @Test
    void toMarkdown_fullDetail_returnsCompleteMarkdown() {
        TestSuite testSuite = aTestSuite()
            .withTitle("Dev team simulator test")
            .withSummary(aTestSuiteSummary().withTestCase(1).withPassed(1))
            .withTestCases(List.of(
                aTestCase()
                    .withWordify("Given developer is \"Alice\"\nWhen developer drinks coffee\nThen developer gets performance boost")
                    .withStatus(Status.PASSED)
                    .withNotes(new Notes(Collections.emptyList(), List.of("sequenceDiagram\nUser->>Simulator: coffee")))
            ))
            .build();

        String markdown = TestSuiteMarkdownFactory.toMarkdown(testSuite, "FULL", false);

        assertThat(markdown).contains("### Test Suite: Dev team simulator test");
        assertThat(markdown).contains("**Summary:** Tests: 1, Passed: 1");
        assertThat(markdown).contains("#### Scenario: Given developer is \"Alice\" [PASSED]");
        assertThat(markdown).contains("**Steps:**");
        assertThat(markdown).contains("When developer drinks coffee");
        assertThat(markdown).contains("```mermaid");
        assertThat(markdown).contains("sequenceDiagram");
    }

    @Test
    void toMarkdown_summaryLevel_returnsOnlySummary() {
        TestSuite testSuite = aTestSuite()
            .withTitle("Dev team simulator test")
            .withSummary(aTestSuiteSummary().withTestCase(2).withPassed(2))
            .withTestCases(List.of(
                aTestCase().withWordify("Scenario 1").withStatus(Status.PASSED),
                aTestCase().withWordify("Scenario 2").withStatus(Status.PASSED)
            ))
            .build();

        String markdown = TestSuiteMarkdownFactory.toMarkdown(testSuite, "SUMMARY", false);

        assertThat(markdown).contains("### Test Suite: Dev team simulator test");
        assertThat(markdown).contains("**Summary:** Tests: 2, Passed: 2");
        assertThat(markdown).doesNotContain("**Steps:**");
        assertThat(markdown).doesNotContain("Scenario 1");
    }

    @Test
    void toMarkdown_stepsOnlyLevel_returnsOnlySteps() {
        TestSuite testSuite = aTestSuite()
            .withTitle("Dev team simulator test")
            .withSummary(aTestSuiteSummary().withTestCase(1).withPassed(1))
            .withTestCases(List.of(
                aTestCase()
                    .withWordify("Steps here")
                    .withStatus(Status.PASSED)
                    .withNotes(new Notes(List.of("Interaction"), Collections.emptyList()))
            ))
            .build();

        String markdown = TestSuiteMarkdownFactory.toMarkdown(testSuite, "STEPS_ONLY", false);

        assertThat(markdown).contains("### Test Suite: Dev team simulator test");
        assertThat(markdown).doesNotContain("**Summary:**");
        assertThat(markdown).contains("#### Scenario: Steps here [PASSED]");
        assertThat(markdown).contains("**Steps:**");
        assertThat(markdown).doesNotContain("**Interactions:**");
    }

    @Test
    void toMarkdown_optimized_stripsSuiteNotes() {
        TestSuite testSuite = aTestSuite()
            .withTitle("Suite")
            .withNotes(new Notes(List.of("Suite Note"), Collections.emptyList()))
            .withSummary(aTestSuiteSummary().withTestCase(0))
            .build();

        String markdownOptimized = TestSuiteMarkdownFactory.toMarkdown(testSuite, "FULL", true);
        String markdownNotOptimized = TestSuiteMarkdownFactory.toMarkdown(testSuite, "FULL", false);

        assertThat(markdownOptimized).doesNotContain("Suite Note");
        assertThat(markdownNotOptimized).contains("Suite Note");
    }
}
