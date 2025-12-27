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

import org.junit.jupiter.api.Test;

import static io.techthinking.flowbdd.report.mermaid.Squence.MessageBuilder.aMessage;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.ARROW_SYNC_DOTTED;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.LINE;
import static io.techthinking.flowbdd.report.mermaid.Squence.MessageType.LINE_DOTTED;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *  <div class="mermaid">
 *  sequenceDiagram
 *     participant Alice
 *     participant Bob
 *     Alice->>John: Hello John, how are you?
 *     loop Healthcheck
 *         John->>John: Fight against hypochondria
 *     end
 *     Note right of John: Rational thoughts <br/>prevail!
 *     John-->>Alice: Great!
 *     John->>Bob: How about you?
 *     Bob-->>John: Jolly good!
 *   </div>
 */
class SequenceDiagramTest {

    @Test
    void basicSequenceDiagramNoBuilders() {
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addParticipant("Alice");
        diagram.addParticipant("Bob");
        diagram.add(new Message("Alice", "Bob", "Hello John, how are you?"));
        diagram.add(new Message("Bob", "Alice", "Great!"));
        assertThat(diagram.generate()).isEqualTo(sequenceDiagram);
    }

    @Test
    void basicSequenceDiagramWithTwoParticipants() {
        SequenceDiagram diagram = new SequenceDiagram()
            .addParticipant("Alice")
            .addParticipant("Bob");
        diagram
            .add(new Message("Alice", "Bob", "Hello John, how are you?"))
            .add(new Message("Bob", "Alice", "Great!"));
        assertThat(diagram.generate()).isEqualTo(sequenceDiagram);
    }

    @Test
    void basicSequenceDiagramWithTwoParticipantsWithBuilders() {
        SequenceDiagram diagram = new SequenceDiagram()
            .addParticipant("Alice")
            .addParticipant("Bob");
        diagram
            .add(aMessage().from("Alice").to("Bob").text("Hello John, how are you?"))
            .add(aMessage().from("Bob").to("Alice").text("Great!"));
        assertThat(diagram.generate()).isEqualTo(sequenceDiagram);
    }

    @Test
    void basicSequenceDiagramAnActorIsGenerated() {
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addActor("Alice");
        diagram.addParticipant("Bob");
        diagram.add(new Message("Alice", "Bob", "Hello John, how are you?"));
        diagram.add(new Message("Bob", "Alice", "Great!"));
        assertThat(diagram.generate()).isEqualTo(
            "sequenceDiagram\n" +
                "\tactor Alice\n" +
                "\tparticipant Bob\n" +
                "\tAlice->>Bob: Hello John, how are you?\n" +
                "\tBob->>Alice: Great!"
        );
    }

    @Test
    void basicSequenceDiagramWithDottedResponses() {
        SequenceDiagram diagram = new SequenceDiagram()
            .addParticipant("Alice")
            .addParticipant("Bob");
        diagram
            .add(aMessage().from("Alice").to("Bob").text("Hello John, how are you?"))
            .add(aMessage().from("Bob").to("Alice").text("Great!").type(ARROW_SYNC_DOTTED));
        assertThat(diagram.generate()).contains("Bob-->>Alice: Great!");
    }

    @Test
    void basicSequenceDiagramWithLineTypes() {
        SequenceDiagram diagram = new SequenceDiagram()
            .addParticipant("User")
            .addParticipant("System");
        diagram
            .add(aMessage().from("User").to("System").text("Solid line without arrow").type(LINE))
            .add(aMessage().from("User").to("System").text("Dotted line without arrow").type(LINE_DOTTED));
        String generated = diagram.generate();
        assertThat(generated).contains("User->System: Solid line without arrow");
        assertThat(generated).contains("User-->System: Dotted line without arrow");
    }

    private static final String NEW_LINE = System.lineSeparator();
    private static final String sequenceDiagram = "sequenceDiagram" + NEW_LINE +
        "\tparticipant Alice" + NEW_LINE +
        "\tparticipant Bob" + NEW_LINE +
        "\tAlice->>Bob: Hello John, how are you?" + NEW_LINE +
        "\tBob->>Alice: Great!";
}
