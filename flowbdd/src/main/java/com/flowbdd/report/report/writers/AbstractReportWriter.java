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

package com.flowbdd.report.report.writers;

import com.flowbdd.report.report.filehandling.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public abstract class AbstractReportWriter {
    private static final Logger log = LoggerFactory.getLogger(AbstractReportWriter.class.getName());
    final FileRepository fileRepository = new FileRepository();
    final FileNameProvider fileNameProvider;

    public AbstractReportWriter(FileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    public void prepareDataDirectory() {
        final Path path = fileNameProvider.path();
        try {
            if (Files.exists(path)) {
                Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .forEach(this::deleteIfExists);
            }
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteIfExists(Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    void write(Path path, String contents, String prefix) {
        if (!Files.exists(path)) {
            fileRepository.create(path);
        }
        fileRepository.update(path, contents);
        System.out.println(prefix + " file://" + path);
    }
}
