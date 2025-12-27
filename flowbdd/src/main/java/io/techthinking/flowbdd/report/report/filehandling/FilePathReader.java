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

package io.techthinking.flowbdd.report.report.filehandling;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

/**
 * Credit to the original source:
 *  https://github.com/eugenp/tutorials/blob/master/testing-modules/mocks/src/main/java/com/baeldung/jimfs/FilePathReader.java
 */
class FilePathReader {

    String getSystemPath(final Path path) {
        try {
            return path
              .toRealPath()
              .toString();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
