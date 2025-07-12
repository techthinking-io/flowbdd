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

package io.techthinking.flowbdd.report.mermaid.Squence;

import io.techthinking.flowbdd.report.mermaid.DiagramTestUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.techthinking.flowbdd.report.mermaid.Squence.MessageBuilder.aMessage;
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

    @Test
    void generateSequenceDiagramAndWriteToFile() throws IOException {
        // Create a sequence diagram
        SequenceDiagram diagram = new SequenceDiagram()
            .addActor("User")
            .addParticipant("System");

        //https://mermaid.js.org/syntax/sequenceDiagram.html
        // Type	Description
        //->	 Solid line without arrow
        //-->	 Dotted line without arrow
        //->>	 Solid line with arrowhead
        //-->>	 Dotted line with arrowhead
        //<<->>	 Solid line with bidirectional arrowheads (v11.0.0+)
        //<<-->> Dotted line with bidirectional arrowheads (v11.0.0+)
        //-x	 Solid line with a cross at the end
        //--x	 Dotted line with a cross at the end
        //-)	 Solid line with an open arrow at the end (async)
        //--)	 Dotted line with a open arrow at the end (async)
        diagram.add(
            //aNote().participant("User").text("Solid lines are default, dotted responses"),
            aMessage().from("User").to("System").text("Solid line without arrow").type("->"),
            aMessage().from("User").to("System").text("Dotted line without arrow").type("-->"),

            aMessage().from("User").to("System").text("Solid line with arrowhead - Simple Request - Correct usage").type("->>"),
            aMessage().from("System").to("User").text("Solid line with arrowhead - Simple Request - Simplified it's okay").type("->>"),
            aMessage().from("User").to("System").text("Dotted line with arrowhead - Simple responses - Dotted for response").type("-->>"),
            aMessage().from("System").to("User").text("Dotted line with arrowhead - Simple responses - Correct usage").type("-->>"),
            aMessage().from("User").to("System").text("Solid line with a cross at the end").type("-x"),
            aMessage().from("System").to("User").text("Solid line with a cross at the end").type("-x"),
            aMessage().from("User").to("System").text("Dotted line with a cross at the end").type("--x"),
            aMessage().from("System").to("User").text("Dotted line with a cross at the end").type("--x"),

            aMessage().from("User").to("System").text("Solid line with an open arrow at the end (async)").type("-)"),
            aMessage().from("System").to("User").text("Solid line with an open arrow at the end (async)").type("-)"),
            aMessage().from("User").to("System").text("Dotted line with an open arrow at the end (async)").type("--)"),
            aMessage().from("System").to("User").text("Dotted line with an open arrow at the end (async)").type("--)")
        );

        // Generate the diagram content
        String diagramContent = diagram.generate();

        // Write to file using the utility
        String filePath = DiagramTestUtil.writeDiagramToFile(
            diagramContent,
            "sequence-diagram.md",
            "Sequence Diagram"
        );

        // Verify file exists and contains the expected content
        assertThat(Paths.get(filePath).toFile()).exists();
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        String loadOutputDiagram = DiagramTestUtil.loadOutputDiagram("sequence-diagram.md");
        assertThat(fileContent).isEqualTo(loadOutputDiagram);
        System.out.println("Sequence diagram written to: " + filePath);
    }

    @Test
    void generateComplexSequenceDiagramAndWriteToFile() throws IOException {
        // Create a sequence diagram with more complex elements
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addActor("Customer")
            .addParticipant("WebApp")
            .addParticipant("API")
            .addParticipant("Database");

        MessageBuilder message = aMessage().from("Customer").to("WebApp").text("Login")
            .from("WebApp").to("API").text("Authenticate")
            .from("API").to("Database").text("Verify credentials")
            .from("Database").to("API").text("User authenticated").type("-->>")
            .from("API").to("WebApp").text("Authentication successful").type("-->>")
            .from("WebApp").to("Customer").text("Welcome!").type("-->>");

        diagram.add(message);

        // Generate the diagram content
        String diagramContent = diagram.generate();

        // Write to file using the utility
        String filePath = DiagramTestUtil.writeDiagramToFile(
            diagramContent,
            "complex-sequence-diagram.md",
            "Complex Sequence Diagram"
        );

        // Verify file exists and contains the expected content
        assertThat(Paths.get(filePath).toFile()).exists();
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        assertThat(fileContent).contains("actor Customer");
        assertThat(fileContent).contains("participant WebApp");
        assertThat(fileContent).contains("participant API");
        assertThat(fileContent).contains("participant Database");

        System.out.println("Complex sequence diagram written to: " + filePath);
    }
}
