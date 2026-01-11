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

package io.techthinking.flowbdd.report.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FlowBddConfig {
    private static final String DEFAULT_BASE_NAME = "flowbdd";
    private static final String DEFAULT_DATA_DIR = "data";
    private static final String DEFAULT_REPORT_DIR = "report";
    /**
     * Set basepath, for testing on another file system you set this
     */
    private static Path overriddenBasePath = null;

    /**
     * Resolves the base directory.
     * Priority: System Property -> Environment Variable -> Temp Dir
     */
    public static Path getBasePath() {
        if (overriddenBasePath != null) return overriddenBasePath;

        String property = System.getProperty("flowbdd.base.dir");
        if (property != null) return Paths.get(property);

        String env = System.getenv("FLOWBDD_BASE_DIR");
        if (env != null) return Paths.get(env);

        return Paths.get(System.getProperty("java.io.tmpdir"), DEFAULT_BASE_NAME);
    }

    public static Path getDataPath() {
        return getBasePath().resolve(System.getProperty("flowbdd.data.dir", DEFAULT_DATA_DIR));
    }

    public static Path getReportPath() {
        return getBasePath().resolve(System.getProperty("flowbdd.report.dir", DEFAULT_REPORT_DIR));
    }

    //TODO maybe setBasePathProperty?
//    public static void setBasePath(String path) {
//        System.setProperty("flowbdd.base.dir", path);
//    }
//
//    public static void setBasePath(Path path) {
//        System.setProperty("flowbdd.base.dir", path.toString());
//    }

    public static void overrideBasePath(Path path) {
        overriddenBasePath = path;
    }

    public static Optional<Path> getOverriddenBasePath() {
        return Optional.ofNullable(overriddenBasePath);
    }
}
