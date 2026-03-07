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

package io.techthinking.flowbdd.report.report.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class TestSuiteMarkdownIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path examplesPath = findExamplesPath();

    private Path findExamplesPath() {
        Path path = Paths.get("src", "test", "resources", "examples", "markdown");
        if (!Files.exists(path)) {
            path = Paths.get("flowbdd", "src", "test", "resources", "examples", "markdown");
        }
        return path;
    }

    @Test
    @DisplayName("Capture Validation: Verify Dev Team Simulator Markdown output")
    void verifyDevTeamSimulatorMarkdown() throws IOException {
        verifyMarkdownForExample("dev-team-simulator");
    }

    private void verifyMarkdownForExample(String exampleName) throws IOException {
        Path jsonPath = examplesPath.resolve(exampleName + ".json");
        Path mdPath = examplesPath.resolve(exampleName + ".md");

        assertThat(jsonPath).exists();

        String jsonContent = Files.readString(jsonPath);
        TestSuite testSuite = objectMapper.readValue(jsonContent, TestSuite.class);

        String generatedMarkdown = TestSuiteMarkdownFactory.toMarkdown(testSuite, "FULL", false);

        // If regenerate property is set, update the stored markdown
        if (Boolean.getBoolean("regenerateExamples") || !Files.exists(mdPath)) {
            Files.writeString(mdPath, generatedMarkdown);
        }

        assertThat(mdPath).exists();
        String expectedMarkdown = Files.readString(mdPath);

        assertThat(generatedMarkdown)
            .as("Generated markdown for %s should match stored example. " +
                "Use -DregenerateExamples=true to update if changes are intentional.", exampleName)
            .isEqualTo(expectedMarkdown);
    }
}
