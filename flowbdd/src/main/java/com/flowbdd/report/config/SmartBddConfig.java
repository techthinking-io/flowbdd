/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2022  James Bayliss
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

package com.flowbdd.report.config;

import java.nio.file.Path;
import java.util.Optional;

import static java.lang.System.getProperty;

public class SmartBddConfig  {
    private static final String dataFolder = "flowbdd/data/";
    private static final String reportFolder = "flowbdd/report/";
    private static String defaultBaseFolder = getProperty("java.io.tmpdir");
    private static Path overriddenBasePath = null;

    public static void setBaseFolder(String folder) {
        defaultBaseFolder = folder;
    }

    public static String getDataFolder() {
        return dataFolder;
    }

    public static String getReportFolder() {
        return reportFolder;
    }

    public static String getDefaultBaseFolder() {
        return defaultBaseFolder;
    }

    public static void overrideBasePath(Path path) {
        overriddenBasePath = path;
    }

    public static Optional<Path> getOverriddenBasePath() {
        return Optional.ofNullable(overriddenBasePath);
    }
}
