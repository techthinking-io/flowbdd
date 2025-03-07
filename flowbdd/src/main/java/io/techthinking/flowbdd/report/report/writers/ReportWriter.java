/*
 * Flow BDD - The productive way to test.
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

package io.techthinking.flowbdd.report.report.writers;

import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.report.report.model.TestSuite;
import io.techthinking.flowbdd.report.report.model.VersionInfo;

/**
 * Will need a strategy - write the results/report and or http post the results/report
 */
public class ReportWriter {
    private final DataReportWriter dataReportWriter = new DataReportWriter(new DataFileNameProvider());
    private final HtmlReportWriter htmlReportWriter = new HtmlReportWriter(new HtmlFileNameProvider());

    public void write(Report report, VersionInfo versionInfo) {
        dataReportWriter.write(report.getIndex());
        htmlReportWriter.write(report.getIndex(), versionInfo);
        report.getTestSuites().forEach(ts -> write(ts, versionInfo));
    }

    public void write(TestSuite testSuite, VersionInfo versionInfo) {
        dataReportWriter.write(testSuite);
        htmlReportWriter.write(testSuite, versionInfo);
    }

    public void prepare() {
        dataReportWriter.prepareDataDirectory();
        htmlReportWriter.prepareDataDirectory();
    }
}
