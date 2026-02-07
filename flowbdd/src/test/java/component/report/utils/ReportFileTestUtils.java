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

package component.report.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.techthinking.flowbdd.report.report.writers.FileNameProvider;
import io.techthinking.flowbdd.report.report.writers.DataReportWriter;
import io.techthinking.flowbdd.report.report.filehandling.FileRepository;
import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbdd.report.report.model.TestSuite;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import static java.lang.System.getProperty;

public class ReportFileTestUtils {
    public static final String DATA_FOLDER = "data/";
    public static final String BASE_FOLDER = getProperty("java.io.tmpdir");

    private final FileSystem fileSystem;
    private final FileRepository fileRepository = new FileRepository();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ReportFileTestUtils(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public static void writeReport(Report report, FileNameProvider fileNameProvider) {
        DataReportWriter dataReportWriter = new DataReportWriter(fileNameProvider);
        dataReportWriter.write(report.getIndex());
    }

    public TestSuite loadTestSuite(Class<?> clazz) throws IOException {
        String contents = fileRepository.read(testSuiteFile(clazz));
        return MAPPER.readValue(contents, TestSuite.class);
    }

    public TestSuite loadTestSuite(String clazz) throws IOException {
        String contents = fileRepository.read(testSuiteFile(clazz));
        return MAPPER.readValue(contents, TestSuite.class);
    }

    public TestSuite loadTestSuite(Path path) throws IOException {
        return MAPPER.readValue(fileRepository.read(path), TestSuite.class);
    }

    public DataReportIndex loadReportIndex() throws IOException {
        String contents = fileRepository.read(homePageFile());
        return MAPPER.readValue(contents, DataReportIndex.class);
    }

    public DataReportIndex loadReportIndex(Path path) throws IOException {
        String contents = fileRepository.read(path);
        return MAPPER.readValue(contents, DataReportIndex.class);
    }

    public Path homePageFile() {
        return dataPath().resolve("index.json");
    }

    public Path testSuiteFile(Class<?> clazz) {
        return dataPath().resolve("TEST-" + clazz.getName() + ".json");
    }

    public Path testSuiteFile(String clazz) {
        return dataPath().resolve("TEST-" + clazz + ".json");
    }

    public Path dataPath() {
        return fileSystem.getPath(BASE_FOLDER, DATA_FOLDER);
    }
}
