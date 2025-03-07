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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Credit to the original source:
 *  https://github.com/eugenp/tutorials/blob/master/testing-modules/mocks/src/main/java/com/baeldung/jimfs/FileRepository.java
 */
public class FileRepository {

    public void create(final Path path, final String fileName) {
        create(path.resolve(fileName));
    }

    public void create(final Path path) {
        try {
            Files.createFile(path);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public String read(final Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public String update(final Path path, final String newContent) {
        try {
            Files.write(path, newContent.getBytes());
            return newContent;
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public void delete(final Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
