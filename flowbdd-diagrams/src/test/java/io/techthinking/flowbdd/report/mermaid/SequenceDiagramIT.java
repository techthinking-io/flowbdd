/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2025  James Bayliss
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

package io.techthinking.flowbdd.report.mermaid;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.techthinking.flowbdd.report.mermaid.MessageBuilder.aMessage;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for SequenceDiagram that writes to a file.
 * The generated file can be viewed in IntelliJ IDEA with the Mermaid plugin.
 *
 * <div class="mermaid">
 * sequenceDiagram
 *    participant User
 *    participant System
 *    participant Database
 *    User->>System: Request data
 *    System->>Database: Query data
 *    Database-->>System: Return data
 *    System-->>User: Display data
 * </div>
 */
class SequenceDiagramIT {

    private static final String OUTPUT_DIR = "src/test/resources/mermaid-diagrams";
    private static final String DIAGRAM_FILE = OUTPUT_DIR + "/sequence-diagram.md";
    private Path outputDirPath;
    private Path diagramFilePath;

    @BeforeEach
    void setUp() throws IOException {
        outputDirPath = Paths.get(OUTPUT_DIR);
        diagramFilePath = Paths.get(DIAGRAM_FILE);

        // Create output directory if it doesn't exist
        if (!Files.exists(outputDirPath)) {
            Files.createDirectories(outputDirPath);
        }
    }

    @AfterEach
    void tearDown() {
        // We don't delete the files after the test so they can be viewed in the IDE
    }

    @Test
    void generateSequenceDiagramAndWriteToFile() throws IOException {
        // Create a sequence diagram
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addParticipant("User");
        diagram.addParticipant("System");
        diagram.addParticipant("Database");

        diagram.add(aMessage().from("User").to("System").text("Request data"));
        diagram.add(aMessage().from("System").to("Database").text("Query data"));
        diagram.add(aMessage().from("Database").to("System").text("Return data").type("-->>"));
        diagram.add(aMessage().from("System").to("User").text("Display data").type("-->>"));

        // Generate the diagram content
        String diagramContent = diagram.generate();

        // Wrap the content in Markdown format for Mermaid
        String markdownContent = "# Sequence Diagram\n\n" +
                "```mermaid\n" +
                diagramContent + "\n" +
                "```\n";

        // Write to file
        Files.write(diagramFilePath, markdownContent.getBytes());

        // Verify file exists and contains the expected content
        assertThat(diagramFilePath.toFile()).exists();
        String fileContent = new String(Files.readAllBytes(diagramFilePath));
        assertThat(fileContent).contains("sequenceDiagram");
        assertThat(fileContent).contains("participant User");
        assertThat(fileContent).contains("participant System");
        assertThat(fileContent).contains("participant Database");
        assertThat(fileContent).contains("User->>System: Request data");

        System.out.println("Sequence diagram written to: " + diagramFilePath.toAbsolutePath());
    }

    @Test
    void generateComplexSequenceDiagramAndWriteToFile() throws IOException {
        // Create a sequence diagram with more complex elements
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addActor("Customer");
        diagram.addParticipant("WebApp");
        diagram.addParticipant("API");
        diagram.addParticipant("Database");

        diagram.add(aMessage().from("Customer").to("WebApp").text("Login"));
        diagram.add(aMessage().from("WebApp").to("API").text("Authenticate"));
        diagram.add(aMessage().from("API").to("Database").text("Verify credentials"));
        diagram.add(aMessage().from("Database").to("API").text("User authenticated").type("-->>"));
        diagram.add(aMessage().from("API").to("WebApp").text("Authentication successful").type("-->>"));
        diagram.add(aMessage().from("WebApp").to("Customer").text("Welcome!").type("-->>"));

        // Generate the diagram content
        String diagramContent = diagram.generate();

        // Wrap the content in Markdown format for Mermaid
        String markdownContent = "# Complex Sequence Diagram\n\n" +
                "```mermaid\n" +
                diagramContent + "\n" +
                "```\n";

        // Write to file
        Path complexDiagramPath = Paths.get(OUTPUT_DIR + "/complex-sequence-diagram.md");
        Files.write(complexDiagramPath, markdownContent.getBytes());

        // Verify file exists and contains the expected content
        assertThat(complexDiagramPath.toFile()).exists();
        String fileContent = new String(Files.readAllBytes(complexDiagramPath));
        assertThat(fileContent).contains("actor Customer");
        assertThat(fileContent).contains("participant WebApp");
        assertThat(fileContent).contains("participant API");
        assertThat(fileContent).contains("participant Database");

        System.out.println("Complex sequence diagram written to: " + complexDiagramPath.toAbsolutePath());
    }
}
