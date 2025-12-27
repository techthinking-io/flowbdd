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

package io.techthinking.flowbdd.report.mermaid.Squence;

import io.techthinking.flowbdd.report.mermaid.DiagramTestUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.techthinking.flowbdd.report.mermaid.Squence.MessageBuilder.aMessage;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.ARROW_ASYNC;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.ARROW_ASYNC_DOTTED;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.ARROW_SYNC;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.ARROW_SYNC_DOTTED;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.LINE;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.LINE_DOTTED;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.NOT_ARRIVED;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.NOT_ARRIVED_DOTTED;
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
            aMessage().from("User").to("System").text("Notification Request - Solid line without arrow").type(LINE),
            aMessage().from("User").to("System").text("Notification Response - Dotted line without arrow").type(LINE_DOTTED),

            aMessage().from("User").to("System").text("Sync Request - Solid line with arrowhead").type(ARROW_SYNC),
            aMessage().from("System").to("User").text("Sync Request (as a response) - Solid line with arrowhead").type(ARROW_SYNC),
            aMessage().from("User").to("System").text("Sync Response (as a request) -Dotted line with arrowhead").type(ARROW_SYNC_DOTTED),
            aMessage().from("System").to("User").text("Sync Response - Dotted line with arrowhead").type(ARROW_SYNC_DOTTED),
            aMessage().from("User").to("System").text("Request Not Arrive - Solid line with a cross at the end").type(NOT_ARRIVED),
            aMessage().from("System").to("User").text("Request Not Arrive (as a request) - Solid line with a cross at the end").type(NOT_ARRIVED),
            aMessage().from("User").to("System").text("Response Not Arrive (as a request) Dotted line with a cross at the end").type(NOT_ARRIVED_DOTTED),
            aMessage().from("System").to("User").text("Response Not Arrive - Dotted line with a cross at the end").type(NOT_ARRIVED_DOTTED),

            aMessage().from("User").to("System").text("Async Request - Solid line with an open arrow at the end (async)").type(ARROW_ASYNC),
            aMessage().from("System").to("User").text("Async Request (as a response) - Solid line with an open arrow at the end (async)").type(ARROW_ASYNC),
            aMessage().from("User").to("System").text("Async Response (as a request) - Dotted line with an open arrow at the end (async)").type(ARROW_ASYNC_DOTTED),
            aMessage().from("System").to("User").text("Async Response - Dotted line with an open arrow at the end (async)").type(ARROW_ASYNC_DOTTED)
        );

        // Generate the diagram content
        String diagramContent = diagram.generate();

        // Write to file using the utility
        String filePath = DiagramTestUtil.writeDiagramToFile(
            diagramContent,
            "sequence-diagram.md",
            "Sequence Diagram"
        );

        assertThat(Paths.get(filePath).toFile()).exists();
        String actualDiagram = new String(Files.readAllBytes(Paths.get(filePath)));

        String expectedDiagram = DiagramTestUtil.loadReferenceDiagram("sequence-diagram.md");
        assertThat(actualDiagram).isEqualTo(expectedDiagram);
    }
}
