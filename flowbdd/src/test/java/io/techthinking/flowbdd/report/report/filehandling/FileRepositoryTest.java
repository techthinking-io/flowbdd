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

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Credit to the original source:
 *  https://github.com/eugenp/tutorials/blob/master/testing-modules/mocks/src/test/java/com/baeldung/jimfs/FileRepositoryUnitTest.java
 */
class FileRepositoryTest implements FileTestProvider {

    private final FileRepository fileRepository = new FileRepository();

    @Test
    @DisplayName("Should create a file on a file system")
    void givenUnixSystem_whenCreatingFile_thenCreatedInPath() {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        String fileName = "newFile.txt";
        Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertThat(pathToStore.resolve(fileName)).exists();
    }

    @Test
    @DisplayName("Should read the content of the file")
    void givenOSXSystem_whenReadingFile_thenContentIsReturned() throws Exception {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.osX());
        Path origin = fileSystem.getPath(FILE_DIRECTORY).resolve(FILE_NAME);
        createFile(fileSystem, origin);

        String content = fileRepository.read(origin);

        assertThat(content).isEqualTo(FILE_CONTENT);
    }

    @Test
    @DisplayName("Should update the content of the file")
    void givenWindowsSystem_whenUpdatingFile_thenContentHasChanged() throws Exception {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
        Path origin = fileSystem.getPath(FILE_DIRECTORY).resolve(FILE_NAME);
        createFile(fileSystem, origin);
        String newContent = "I'm updating you.";

        String content = fileRepository.update(origin, newContent);

        assertThat(content).isEqualTo(newContent);
        assertThat(fileRepository.read(origin)).isEqualTo(newContent);
    }

    @Test
    @DisplayName("Should delete file")
    void givenCurrentSystem_whenDeletingFile_thenFileHasBeenDeleted() throws Exception {
        FileSystem fileSystem = Jimfs.newFileSystem();
        Path origin = fileSystem.getPath(FILE_DIRECTORY).resolve(FILE_NAME);
        createFile(fileSystem, origin);

        fileRepository.delete(origin);

        assertThat(origin).doesNotExist();
    }

    private void createFile(FileSystem fileSystem, Path origin) throws IOException {
        assertThat(origin).doesNotExist();

        Files.createDirectories(fileSystem.getPath(FILE_DIRECTORY));
        fileRepository.create(origin);
        fileRepository.update(origin, FILE_CONTENT);

        assertThat(fileSystem.getPath(FILE_DIRECTORY).resolve(FILE_NAME)).exists();
    }
}
