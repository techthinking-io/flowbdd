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

package io.techthinking.flowbdd.ft.report.html;

import io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO use https://jsoup.org/
 * */
public class PassingResultsHtmlTest extends AbstractResultsForHtml {

    @Override
    public Class<?> classUnderTest() {
        return ClassUnderTest.class;
    }

    /**
     * Summary of all Tests
     * Summary: passed: 6, skipped: 0, failed: 0, aborted: 0, tests: 6
     *
     * io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest
     */
    @Test
    void generatesIndexJson()  {
        assertThat(reportIndex()).isNotNull();
        assertThat(reportIndex())
            .contains("Summary of all Tests")
            .contains("Summary: passed: 6, skipped: 0, failed: 0, aborted: 0, tests: 6")
            .contains("<a href=\"TEST-io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest.html\">io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest</a>");
    }

    /**
     * <pre>
     * Feature: Class under test
     *
     *  Scenario: Test method (PASSED)
     * Passing assertion
     *
     *  Scenario: Param test (PASSED)
     * Passing assertion with value 1
     *
     *  Scenario: Param test (PASSED)
     * Passing assertion with value 2
     *
     *  Scenario: Param test (PASSED)
     * Passing assertion with value 3
     *
     *  Scenario: Param test with nulls (PASSED)
     * Passing assertion with null value 2
     *
     *  Scenario: Param test with nulls (PASSED)
     * Passing assertion with value 3 null
     * </pre>
     */
    @Test
    void generatesTestSuiteJson() {
        assertThat(testSuite()).isNotNull();
        assertThat(testSuite())
            .contains("<a href=\"./index.html\">index</a>")
            .contains("io.techthinking.flowbdd.ft.undertest.basic.ClassUnderTest")
            .contains("Feature:")
            .contains("Class under test")
            .contains("Scenario:")
            .contains("Test method (PASSED)")
            .contains("Passing assertion")
            .contains("Param test value 1 (PASSED)")
            .contains("Passing assertion with one param value 1")
            .contains("Passing assertion with one param value 2")
            .contains("Passing assertion with one param value 3")
            .contains("Param test with nulls null, value 4 (PASSED)")
            .contains("Passing assertion with two params null value 4")
            .contains("Passing assertion with two params value 5 null");
    }
}
