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

package io.techthinking.flowbdd.ft.report.html;

import io.techthinking.flowbdd.ft.undertest.scenarios.bookstore.BookStoreUnderTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO use https://jsoup.org/
 * TODO work lifecycle testing issues.
 * */
public class BookStoreHtmlTest extends AbstractResultsForHtml {

    @Override
    public Class<?> classUnderTest() {
        return BookStoreUnderTest.class;
    }

    @Test
    void generatesIndexJson()  {
        assertThat(reportIndex()).isNotNull();
        assertThat(reportIndex())
            .contains("Summary of all Tests")
            .contains("Summary: passed: 1, skipped: 0, failed: 0, aborted: 0, tests: 1")
            .contains("<a href=\"TEST-io.techthinking.flowbdd.ft.undertest.scenarios.bookstore.BookStoreUnderTest.html\">io.techthinking.flowbdd.ft.undertest.scenarios.bookstore.BookStoreUnderTest</a>");
    }

    @Test
    void generatesScenario() {
        assertThat(testSuite()).isNotNull();
        assertThat(testSuite())
            .contains("Given a book exists")
            .contains("When a user requests a book")
            .contains("Then the response contains a default isbn book");
    }

    @Test
    void generatesIndexLinks() {
        assertThat(testSuite()).isNotNull();
        assertThat(testSuite())
            .contains("<a href=\"./index.html\">index</a>")
            .contains("io.techthinking.flowbdd.ft.undertest.scenarios.bookstore.BookStoreUnderTest");
    }

    @Test
    void generatesFeatureNotes() {
        assertThat(testSuite())
            .contains("Bookstore Feature notes");
    }

    @Test
    void generatesUml() {
        assertThat(testSuite())
            .contains("<span class=\"mermaid\"")
            .contains("actor User")
            .contains("participant BookStore")
            .contains("participant ISBNdb");
    }
}
