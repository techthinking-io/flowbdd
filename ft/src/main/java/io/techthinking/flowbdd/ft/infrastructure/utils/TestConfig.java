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

package io.techthinking.flowbdd.ft.infrastructure.utils;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import io.techthinking.flowbdd.report.config.SmartBddConfig;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.getProperty;

public class TestConfig {
    public static boolean inMemoryDirectory = true;
    private static final Path basePath = Paths.get(getProperty("java.io.tmpdir"));
    private static final FileSystem inMemoryFileSystem = Jimfs.newFileSystem(Configuration.unix());
    private static final Path inMemoryBasePath = inMemoryFileSystem.getPath("/test-base-path/");

    static {
        if (inMemoryDirectory) {
            SmartBddConfig.overrideBasePath(TestConfig.getBasePath());
        }
    }

    public static Path getBasePath() {
        if (inMemoryDirectory) {
            return inMemoryBasePath;
        }
        return basePath;
    }
}
