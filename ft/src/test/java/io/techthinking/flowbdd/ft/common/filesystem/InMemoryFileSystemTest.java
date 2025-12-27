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

package io.techthinking.flowbdd.ft.common.filesystem;

import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryFileSystemTest {

    @Test
    void writeReadUsingPath() {
        FileSystem fileSystem = Jimfs.newFileSystem();
        Path path = fileSystem.getPath("my-path");

        assertThat(Files.exists(path)).isFalse();
        create(path);
        assertThat(Files.exists(path)).isTrue();
        update(path, "content");
        assertThat(read(path)).isEqualTo("content");
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
}
