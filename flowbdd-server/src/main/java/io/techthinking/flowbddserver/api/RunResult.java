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

package io.techthinking.flowbddserver.api;

import java.util.List;

/** TODO should this be the same from the core project? */
public class RunResult {
    private String status; // SUCCESS, FAILED, ERROR
    private int tests;
    private int passed;
    private int failed;
    private int skipped;
    private int aborted;
    private long timeMillis;
    private List<String> reportLinks; // e.g., file:///tmp/flowbdd/report/...
    private String message;
    private String startedAt;
    private String finishedAt;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getTests() { return tests; }
    public void setTests(int tests) { this.tests = tests; }
    public int getPassed() { return passed; }
    public void setPassed(int passed) { this.passed = passed; }
    public int getFailed() { return failed; }
    public void setFailed(int failed) { this.failed = failed; }
    public int getSkipped() { return skipped; }
    public void setSkipped(int skipped) { this.skipped = skipped; }
    public int getAborted() { return aborted; }
    public void setAborted(int aborted) { this.aborted = aborted; }
    public long getTimeMillis() { return timeMillis; }
    public void setTimeMillis(long timeMillis) { this.timeMillis = timeMillis; }
    public List<String> getReportLinks() { return reportLinks; }
    public void setReportLinks(List<String> reportLinks) { this.reportLinks = reportLinks; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getStartedAt() { return startedAt; }
    public void setStartedAt(String startedAt) { this.startedAt = startedAt; }
    public String getFinishedAt() { return finishedAt; }
    public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }
}
