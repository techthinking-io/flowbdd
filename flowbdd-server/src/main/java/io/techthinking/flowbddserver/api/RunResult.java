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

import java.time.Instant;
import java.util.List;

/** TODO should this be the same from the core project? */
public class RunResult {
    private String status; // SUCCESS, FAILED, ERROR
    private int testsFound;
    private int testsSucceeded;
    private int testsFailed;
    private int testsSkipped;
    private long timeMillis;
    private List<String> reportLinks; // e.g., file:///tmp/flowbdd/report/...
    private String message;
    private Instant startedAt;
    private Instant finishedAt;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getTestsFound() { return testsFound; }
    public void setTestsFound(int testsFound) { this.testsFound = testsFound; }
    public int getTestsSucceeded() { return testsSucceeded; }
    public void setTestsSucceeded(int testsSucceeded) { this.testsSucceeded = testsSucceeded; }
    public int getTestsFailed() { return testsFailed; }
    public void setTestsFailed(int testsFailed) { this.testsFailed = testsFailed; }
    public int getTestsSkipped() { return testsSkipped; }
    public void setTestsSkipped(int testsSkipped) { this.testsSkipped = testsSkipped; }
    public long getTimeMillis() { return timeMillis; }
    public void setTimeMillis(long timeMillis) { this.timeMillis = timeMillis; }
    public List<String> getReportLinks() { return reportLinks; }
    public void setReportLinks(List<String> reportLinks) { this.reportLinks = reportLinks; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public Instant getFinishedAt() { return finishedAt; }
    public void setFinishedAt(Instant finishedAt) { this.finishedAt = finishedAt; }
}
