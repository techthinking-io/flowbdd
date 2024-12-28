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

package io.bitsmart.bdd.report.report.writers;

import io.bitsmart.bdd.report.config.ResolvedSmartBddConfig;
import io.bitsmart.bdd.report.config.SmartBddConfig;
import io.bitsmart.bdd.report.report.model.TestSuite;

import java.nio.file.Path;


public class HtmlFileNameProvider implements FileNameProvider {

    @Override
    public Path path() {
        return ResolvedSmartBddConfig.getBasePath().resolve(SmartBddConfig.getReportFolder());
    }

    @Override
    public Path indexFile() {
        return path().resolve("index.html");
    }

    @Override
    public Path file(TestSuite testSuite) {
        return outputFile(fullyQualifiedName(testSuite));
    }

    private String fullyQualifiedName(TestSuite testSuite) {
        return testSuite.getName();
    }

    private Path outputFile(String testName) {
        return path().resolve("TEST-" + testName + ".html");
    }
}
