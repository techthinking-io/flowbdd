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

import io.techthinking.flowbdd.report.report.writers.HtmlFileNameProvider;

import java.io.IOException;
import java.nio.file.Path;

public class HtmlReportTestUtils {
    private static final HtmlFileNameProvider HTML_FILE_NAME_PROVIDER = new HtmlFileNameProvider();

    public static String loadTestSuite(Class<?> clazz) throws IOException {
        return new FileLoader().read(testSuiteFile(clazz));
    }

    public static String loadReportIndex() throws IOException {
        return new FileLoader().read(homePageFile());
    }

    public static Path homePageFile() {
        return HTML_FILE_NAME_PROVIDER.indexFile();
    }

    public static Path testSuiteFile(Class<?> clazz) {
        return HTML_FILE_NAME_PROVIDER.file("TEST-" + clazz.getCanonicalName());
    }

    public static Path outputDirectory() {
        return HTML_FILE_NAME_PROVIDER.path();
    }
}
