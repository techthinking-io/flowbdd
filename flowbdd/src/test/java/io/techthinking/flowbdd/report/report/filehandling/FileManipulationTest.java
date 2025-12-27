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

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Credit to the original source:
 *  https://github.com/eugenp/tutorials/blob/master/testing-modules/mocks/src/test/java/com/baeldung/jimfs/FileManipulationUnitTest.java
 */
class FileManipulationTest implements FileTestProvider {

    private final FileManipulation fileManipulation = new FileManipulation();
    private final FileRepository fileRepository = new FileRepository();

    private static Stream<Arguments> provideFileSystem() {
        return Stream.of(Arguments.of(Jimfs.newFileSystem(Configuration.unix())), Arguments.of(Jimfs.newFileSystem(Configuration.windows())), Arguments.of(Jimfs.newFileSystem(Configuration.osX())));
    }

    @ParameterizedTest
    @DisplayName("Should move file to new destination")
    @MethodSource("provideFileSystem")
    void givenEachSystem_whenMovingFile_thenMovedToNewPath(final FileSystem fileSystem) throws Exception {
        Path origin = fileSystem.getPath(FILE_DIRECTORY).resolve(FILE_NAME);
        createFile(fileSystem, origin);
        Path destination = fileSystem.getPath("newDirectory", FILE_NAME);

        fileManipulation.move(origin, destination);

        assertThat(origin).doesNotExist();
        assertThat(destination).exists();
    }

    private void createFile(FileSystem fileSystem, Path origin) throws IOException {
        assertThat(origin).doesNotExist();

        Files.createDirectories(fileSystem.getPath(FILE_DIRECTORY));
        fileRepository.create(origin);
        fileRepository.update(origin, FILE_CONTENT);

        assertThat(fileSystem.getPath(FILE_DIRECTORY).resolve(FILE_NAME)).exists();
    }
}