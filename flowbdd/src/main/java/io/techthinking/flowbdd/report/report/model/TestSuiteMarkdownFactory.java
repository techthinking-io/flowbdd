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

import io.techthinking.flowbdd.report.config.FlowBddConfig;
import io.techthinking.flowbdd.report.report.model.notes.Notes;

import java.util.List;

/**
 * Factory class to convert TestSuite JSON representation to Markdown.
 * Designed to be independent, configurable, and testable.
 */
public class TestSuiteMarkdownFactory {

    public static String toMarkdown(TestSuite testSuite) {
        return toMarkdown(testSuite, FlowBddConfig.getAiDetailLevel(), FlowBddConfig.isAiOptimized());
    }

    public static String toMarkdown(TestSuite testSuite, String detailLevel, boolean optimized) {
        StringBuilder sb = new StringBuilder();

        sb.append("### Test Suite: ").append(testSuite.getTitle()).append("\n");

        if (testSuite.getNotes() != null && !optimized) {
            appendNotes(sb, testSuite.getNotes(), "Notes");
        }

        if ("SUMMARY".equalsIgnoreCase(detailLevel)) {
            appendSummary(sb, testSuite.getSummary());
            return sb.toString();
        }

        if (!"STEPS_ONLY".equalsIgnoreCase(detailLevel)) {
            appendSummary(sb, testSuite.getSummary());
        }

        List<TestCase> testCases = testSuite.getTestCases();
        if (testCases != null) {
            for (TestCase testCase : testCases) {
                appendTestCase(sb, testCase, detailLevel, optimized);
            }
        }

        return sb.toString();
    }

    private static void appendSummary(StringBuilder sb, TestSuiteSummary summary) {
        if (summary == null) return;
        sb.append("\n**Summary:** ")
          .append("Tests: ").append(summary.getTests())
          .append(", Passed: ").append(summary.getPassed())
          .append(", Failed: ").append(summary.getFailed())
          .append(", Skipped: ").append(summary.getSkipped())
          .append(", Aborted: ").append(summary.getAborted())
          .append("\n");
    }

    private static void appendTestCase(StringBuilder sb, TestCase testCase, String detailLevel, boolean optimized) {
        sb.append("\n#### Scenario: ").append(testCase.getWordify().lines().findFirst().orElse("Unknown Scenario"));
        sb.append(" [").append(testCase.getStatus()).append("]\n");

        if (!"SUMMARY".equalsIgnoreCase(detailLevel)) {
            sb.append("**Steps:**\n");
            sb.append(testCase.getWordify()).append("\n");

            if (!"STEPS_ONLY".equalsIgnoreCase(detailLevel)) {
                if (testCase.getNotes() != null) {
                    appendNotes(sb, testCase.getNotes(), "Interactions");
                }
                
                if (testCase.getCause() != null) {
                    sb.append("\n**Failure Cause:**\n");
                    sb.append("```\n").append(testCase.getCause().getMessage()).append("\n```\n");
                }
            }
        }
    }

    private static void appendNotes(StringBuilder sb, Notes notes, String title) {
        if (notes == null) return;

        List<String> textNotes = notes.getTextNotes();
        if (textNotes != null && !textNotes.isEmpty()) {
            sb.append("\n**").append(title).append(":**\n");
            for (String note : textNotes) {
                sb.append("- ").append(note).append("\n");
            }
        }

        List<String> diagrams = notes.getDiagrams();
        if (diagrams != null && !diagrams.isEmpty()) {
            if (textNotes == null || textNotes.isEmpty()) {
                sb.append("\n**").append(title).append(":**\n");
            }
            for (String diagram : diagrams) {
                if (diagram.contains("sequenceDiagram") || diagram.contains("graph TD")) {
                    sb.append("\n```mermaid\n").append(diagram).append("\n```\n");
                } else {
                    sb.append("- ").append(diagram).append("\n");
                }
            }
        }
    }
}
