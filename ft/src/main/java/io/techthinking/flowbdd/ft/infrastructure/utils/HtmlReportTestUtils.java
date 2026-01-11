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

package io.techthinking.flowbdd.ft.infrastructure.utils;

import java.io.IOException;
import java.nio.file.Path;

public class HtmlReportTestUtils {
    public static String loadTestSuite(Class<?> clazz) throws IOException {
        return new FileLoader().read(testSuiteFile(clazz));
    }

    public static String loadReportIndex() throws IOException {
        return new FileLoader().read(homePageFile());
    }

    public static Path homePageFile() {
        return outputDirectory().resolve("index.html");
    }

    public static Path testSuiteFile(Class<?> clazz) {
        return outputDirectory().resolve("TEST-" + clazz.getCanonicalName() + ".html");
    }

    public static Path outputDirectory() {
        return TestConfig.getBasePath().resolve("report/");
    }
}
