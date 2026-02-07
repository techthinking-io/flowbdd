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

package io.techthinking.flowbddserver.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.techthinking.flowbdd.report.config.FlowBddConfig;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbdd.report.report.model.TestSuite;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/index")
    public DataReportIndex getIndex() {
        Path indexPath = FlowBddConfig.getDataPath().resolve("index.json");
        if (!Files.exists(indexPath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report index not found at " + indexPath);
        }
        try {
            return objectMapper.readValue(indexPath.toFile(), DataReportIndex.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read report index", e);
        }
    }

    @GetMapping("/suite/{fileName}")
    public TestSuite getTestSuite(@PathVariable String fileName) {
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        Path suitePath = FlowBddConfig.getDataPath().resolve(fileName);
        if (!Files.exists(suitePath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test suite not found at " + suitePath);
        }
        try {
            return objectMapper.readValue(suitePath.toFile(), TestSuite.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read test suite", e);
        }
    }

    @GetMapping("/suites")
    public java.util.List<io.techthinking.flowbdd.report.report.model.TestSuiteNameToFile> getSuites() {
        DataReportIndex index = getIndex();
        return index.getLinks().getTestSuites();
    }

    @GetMapping("/classes")
    public java.util.List<String> getClasses() {
        DataReportIndex index = getIndex();
        return index.getLinks().getTestSuites().stream()
                .map(io.techthinking.flowbdd.report.report.model.TestSuiteNameToFile::getName)
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/filenames")
    public java.util.List<String> getFileNames() {
        DataReportIndex index = getIndex();
        return index.getLinks().getTestSuites().stream()
                .map(io.techthinking.flowbdd.report.report.model.TestSuiteNameToFile::getFile)
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/suite/class/{className}")
    public TestSuite getTestSuiteByClass(@PathVariable String className) {
        DataReportIndex index = getIndex();
        String fileName = index.getLinks().getTestSuites().stream()
            .filter(link -> link.getName().equals(className))
            .map(io.techthinking.flowbdd.report.report.model.TestSuiteNameToFile::getFile)
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test suite not found for class " + className));

        return getTestSuite(fileName);
    }
}
