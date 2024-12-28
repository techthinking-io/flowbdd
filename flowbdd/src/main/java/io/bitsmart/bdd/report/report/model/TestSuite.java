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

package io.bitsmart.bdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bitsmart.bdd.report.report.model.notes.Notes;

import java.util.List;
import java.util.Objects;

public class TestSuite {
    private final String title;
    private final String name;
    private final String className;
    private final String packageName;
//    private final List<String> methodNames;
    private final List<TestCase> testCases;
    private final TestSuiteSummary summary;
    private final Notes notes;
    // setup, teardown metrics
    // time started
    // time taken

    @JsonCreator
    public TestSuite(
        @JsonProperty("title") String title,
        @JsonProperty("name") String name,
        @JsonProperty("className") String className,
        @JsonProperty("packageName") String packageName,
        @JsonProperty("testResults") List<TestCase> testCases,
        @JsonProperty("summary") TestSuiteSummary summary,
        @JsonProperty("notes") Notes notes)
    {
        this.title = title;
        this.name = name;
        this.className = className;
        this.packageName = packageName;
        this.testCases = testCases;
        this.summary = summary;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public TestSuiteSummary getSummary() {
        return summary;
    }

    public Notes getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSuite)) return false;
        TestSuite testSuite = (TestSuite) o;
        return Objects.equals(title, testSuite.title) && Objects.equals(name, testSuite.name) && Objects.equals(className, testSuite.className) && Objects.equals(packageName, testSuite.packageName) && Objects.equals(testCases, testSuite.testCases) && Objects.equals(summary, testSuite.summary) && Objects.equals(notes, testSuite.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, name, className, packageName, testCases, summary, notes);
    }

    @Override
    public String toString() {
        return "TestSuite{" +
            "title='" + title + '\'' +
            ", name='" + name + '\'' +
            ", className='" + className + '\'' +
            ", packageName='" + packageName + '\'' +
//            ", methodNames=" + methodNames +
            ", testCases=" + testCases +
            ", summary=" + summary +
            ", notes=" + notes +
            '}';
    }
}
