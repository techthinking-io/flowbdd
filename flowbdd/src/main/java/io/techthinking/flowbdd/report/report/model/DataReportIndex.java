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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class DataReportIndex {
    private final TestSuiteLinks links;
    private final TestSuiteSummary summary;
    private final String timeStamp;

    @JsonCreator
    public DataReportIndex(
        @JsonProperty("links") TestSuiteLinks links,
        @JsonProperty("summary") TestSuiteSummary summary,
        @JsonProperty("timeStamp") String timeStamp) {
        this.links = links;
        this.summary = summary;
        this.timeStamp = timeStamp;
    }

    public TestSuiteLinks getLinks() {
        return links;
    }

    public TestSuiteSummary getSummary() {
        return summary;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataReportIndex)) return false;
        DataReportIndex that = (DataReportIndex) o;
        return Objects.equals(links, that.links) && Objects.equals(summary, that.summary) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(links, summary, timeStamp);
    }

    @Override
    public String toString() {
        return "DataReportIndex{" +
            "links=" + links +
            ", summary=" + summary +
            ", timeStamp=" + timeStamp +
            '}';
    }
}
