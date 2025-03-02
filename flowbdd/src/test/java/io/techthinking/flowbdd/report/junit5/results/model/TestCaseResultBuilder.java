/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2022  James Bayliss
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

package io.techthinking.flowbdd.report.junit5.results.model;

import io.techthinking.flowbdd.report.junit5.results.model.notes.Notes;

import java.util.ArrayList;
import java.util.List;

public final class TestCaseResultBuilder {
    private String wordify;
    private TestCaseResultStatus status;
    private TestSuiteClass testSuiteClass;
    private List<Object> args = new ArrayList<>();
    private String name;
    private String displayName;
    private Notes notes;

    private TestCaseResultBuilder() {
    }

    public static TestCaseResultBuilder aTestCaseResult() {
        return new TestCaseResultBuilder();
    }

    public TestCaseResultBuilder withWordify(String wordify) {
        this.wordify = wordify;
        return this;
    }

    public TestCaseResultBuilder withStatus(TestCaseResultStatus status) {
        this.status = status;
        return this;
    }

    public TestCaseResultBuilder withTestSuiteClass(TestSuiteClass testSuiteClass) {
        this.testSuiteClass = testSuiteClass;
        return this;
    }

    public TestCaseResultBuilder withArgs(List<Object> args) {
        this.args = args;
        return this;
    }

    public TestCaseResultBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TestCaseResultBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public TestCaseResultBuilder withNotes(Notes notes) {
        this.notes = notes;
        return this;
    }

    public TestCaseResult build() {
        TestCaseResult testCaseResult = new TestCaseResult(testSuiteClass, notes);
        testCaseResult.setWordify(wordify);
        testCaseResult.setStatus(status);
        testCaseResult.setArgs(args);
        testCaseResult.setName(name);
        testCaseResult.setDisplayName(displayName);
        return testCaseResult;
    }
}
