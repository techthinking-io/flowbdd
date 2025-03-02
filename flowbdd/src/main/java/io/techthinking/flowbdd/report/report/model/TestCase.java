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

package io.techthinking.flowbdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.techthinking.flowbdd.report.report.model.notes.Notes;

import java.util.Objects;

/**
 *  testMethods - before, after, undertest
 *  testMethod wordify name? or just contents? uml diagram, notes? drive from annotations?
 *  Note: only TestMethodUndertest has params? id? parent id?
 *
 *  TestMethodUnderTest vs TestMethodSetup  Only underTest has params!!!
 *  TestMethodUnderTest = method name, method name wordify, params, contents wordify, notes (uml and text, static and runtime)
 *  TestMethodSetup = No params.
 *
 *  test suite that has before all and after all
 *  test case that has before each and after each
 *
 *  //TODO classes to add
 *  // TestSuiteWordify // wordify method name or the contents
 *  // - beforeAll
 *  // - beforeEach
 *  // - afterAll
 *  // - afterEach
 *  // TestCaseWordify
 *  // - scenario
 *  // - method
 *  // - methodInvocation with params
 *  // Wordify text/markdown/html
 *  // - text example given something
 *  // - html example span class="given" given something
 *  // - markdown
 *  // Timings
 *  // - beforeAll
 *  // - beforeEach
 *  // - afterAll
 *  // - afterEach
 *  // - testcase
 *  // TestSuiteMethodInvocation
 *  // - args, repeat, etc...
 *  // TODO How to handle repeats?
 *  -- param  tests could have a parent test.
 *  -- repeat tests could have a parent test.
 *  This then can all timings!
 *
 */
public class TestCase {
    private final String wordify;
    private final Status status;   // TODO maybe results { status, cause }
    private final Throwable cause; // TODO maybe results { status, cause }
    private final Method method;   // TODO maybe TestMethodInvocation vs TestMethodInvocation  Only underTest has params!!!
    private final Clazz clazz;
    private final Notes notes;
    private final TestCaseTimings timings; // TODO TestCaseTiming!!!!! Before and After all not needed in this class.

    @JsonCreator
    public TestCase(
        @JsonProperty("wordify") String wordify,
        @JsonProperty("status") Status status,
        @JsonProperty("cause") Throwable cause,
        @JsonProperty("method") Method method,
        @JsonProperty("class") Clazz clazz,
        @JsonProperty("notes") Notes notes,
        @JsonProperty("timings") TestCaseTimings timings) {
        this.wordify = wordify;
        this.status = status;
        this.cause = cause;
        this.method = method;
        this.clazz = clazz;
        this.notes = notes;
        this.timings = timings;
    }

    public String getWordify() {
        return wordify;
    }

    public Status getStatus() {
        return status;
    }

    public Throwable getCause() {
        return cause;
    }

    public Method getMethod() {
        return method;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public Notes getNotes() {
        return notes;
    }

    public TestCaseTimings getTimings() {
        return timings;
    }

    /** ignore timings */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCase)) return false;
        TestCase testCase = (TestCase) o;
        return Objects.equals(wordify, testCase.wordify) && status == testCase.status && Objects.equals(cause, testCase.cause) && Objects.equals(method, testCase.method) && Objects.equals(clazz, testCase.clazz) && Objects.equals(notes, testCase.notes); //&& Objects.equals(timings, testCase.timings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordify, status, cause, method, clazz, notes, timings);
    }

    @Override
    public String toString() {
        return "TestCase{" +
            "wordify='" + wordify + '\'' +
            ", status=" + status +
            ", cause=" + cause +
            ", method=" + method +
            ", clazz=" + clazz +
            ", notes=" + notes +
            ", timings=" + timings +
            '}';
    }
}
