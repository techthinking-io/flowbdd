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

package io.techthinking.flowbdd.report.report.filehandling;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Credit to the original source:
 *  https://github.com/eugenp/tutorials/blob/master/testing-modules/mocks/src/test/java/com/baeldung/jimfs/FilePathReaderUnitTest.java
 */
class FilePathReaderTest {

    private static final String DIRECTORY_NAME = "baeldung";

    private final FilePathReader filePathReader = new FilePathReader();

    @Test
    @DisplayName("Should get path on windows")
    void givenWindowsSystem_shouldGetPath_thenReturnWindowsPath() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
        final Path path = getPathToFile(fileSystem);

        final String stringPath = filePathReader.getSystemPath(path);

        assertEquals("C:\\work\\" + DIRECTORY_NAME, stringPath);
    }

    @Test
    @DisplayName("Should get path on unix")
    void givenUnixSystem_shouldGetPath_thenReturnUnixPath() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        final Path path = getPathToFile(fileSystem);

        final String stringPath = filePathReader.getSystemPath(path);

        assertEquals("/work/" + DIRECTORY_NAME, stringPath);
    }

    private Path getPathToFile(final FileSystem fileSystem) throws Exception {
        final Path path = fileSystem.getPath(DIRECTORY_NAME);
        Files.createDirectory(path);

        return path;
    }
}