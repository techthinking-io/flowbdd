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

package com.flowbdd.report.junit5.results.model;

import com.flowbdd.report.junit5.results.model.notes.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TestCaseResult {
    //TODO classes to add
    // TestSuiteWordify
    // - beforeAll
    // - beforeEach
    // - afterAll
    // - afterEach
    // TestCaseWordify
    // - scenario
    // - method
    // - methodInvocation with params
    // Wordify text/markdown/html
    // - text example given something
    // - html example <span class="given">given</> something
    // - markdown
    // Timings
    // - beforeAll
    // - beforeEach
    // - afterAll
    // - afterEach
    // - testcase
    // TestSuiteMethodInvocation
    // - args, repeat, etc...
    //TODO How to handle repeats?
    private String wordify;
    private String name; // methodName!!
    private String displayName; // methodNameWordify or methodDisplayName ???
    private TestCaseResultStatus status;
    private Throwable cause;
    private List<Object> args = new ArrayList<>();
    private final TestSuiteClass testSuiteClass; // TODO surely this should be class??!?!?
    private final Notes notes;

    public TestCaseResult(TestSuiteClass testSuiteClass, Notes notes) {
        this.testSuiteClass = testSuiteClass;
        this.notes = notes;
    }

    public String getWordify() {
        return wordify;
    }

    public TestCaseResultStatus getStatus() {
        return status;
    }

    public Optional<Throwable> getCause() {
        return Optional.ofNullable(cause);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TestSuiteClass getTestSuiteClass() {
        return testSuiteClass;
    }

    public List<Object> getArgs() {
        return args;
    }

    public Notes getNotes() {
        return notes;
    }

    public TestCaseResult setWordify(String wordify) {
        this.wordify = wordify;
        return this;
    }

    public TestCaseResult setName(String name) {
        this.name = name;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public TestCaseResult setStatus(TestCaseResultStatus status) {
        this.status = status;
        return this;
    }

    public TestCaseResult setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public TestCaseResult setArgs(List<Object> args) {
        this.args = args;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCaseResult)) return false;
        TestCaseResult that = (TestCaseResult) o;
        return Objects.equals(wordify, that.wordify) && Objects.equals(name, that.name) && Objects.equals(displayName, that.displayName) && status == that.status && Objects.equals(cause, that.cause) && Objects.equals(args, that.args) && Objects.equals(testSuiteClass, that.testSuiteClass) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordify, name, displayName, status, cause, args, testSuiteClass, notes);
    }

    @Override
    public String toString() {
        return "TestCaseResult{" +
            "wordify='" + wordify + '\'' +
            ", name='" + name + '\'' +
            ", displayName='" + displayName + '\'' +
            ", status=" + status +
            ", cause=" + cause +
            ", args=" + args +
            ", testSuiteClass=" + testSuiteClass +
            ", notes=" + notes +
            '}';
    }
}
