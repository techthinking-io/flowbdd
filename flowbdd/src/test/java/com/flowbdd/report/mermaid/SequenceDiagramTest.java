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

package com.flowbdd.report.mermaid;

import org.junit.jupiter.api.Test;

import static com.flowbdd.report.mermaid.MessageBuilder.aMessage;
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
    void basicSequenceDiagramWithTwoParticipantsIsGenerated() {
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addParticipant("Alice");
        diagram.addParticipant("Bob");
        diagram.addMessage(new Message("Alice", "Bob", "Hello John, how are you?"));
        diagram.addMessage(new Message("Bob", "Alice", "Great!"));
        assertThat(diagram.generate()).isEqualTo(
            "sequenceDiagram\n" +
            "\tparticipant Alice\n" +
            "\tparticipant Bob\n" +
            "\tAlice->>Bob: Hello John, how are you?\n" +
            "\tBob->>Alice: Great!"
        );
    }

    @Test
    void basicSequenceDiagramWithTwoParticipantsIsGeneratedWithBuilder() {
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addParticipant("Alice");
        diagram.addParticipant("Bob");
        diagram.add(aMessage().from("Alice").to("Bob").text("Hello John, how are you?"));
        diagram.add(aMessage().from("Bob").to("Alice").text("Great!"));

        assertThat(diagram.generate()).isEqualTo(
            "sequenceDiagram\n" +
                "\tparticipant Alice\n" +
                "\tparticipant Bob\n" +
                "\tAlice->>Bob: Hello John, how are you?\n" +
                "\tBob->>Alice: Great!"
        );
    }

    @Test
    void basicSequenceDiagramAnActorIsGenerated() {
        SequenceDiagram diagram = new SequenceDiagram();
        diagram.addActor("Alice");
        diagram.addParticipant("Bob");
        diagram.addMessage(new Message("Alice", "Bob", "Hello John, how are you?"));
        diagram.addMessage(new Message("Bob", "Alice", "Great!"));
        assertThat(diagram.generate()).isEqualTo(
            "sequenceDiagram\n" +
                "\tactor Alice\n" +
                "\tparticipant Bob\n" +
                "\tAlice->>Bob: Hello John, how are you?\n" +
                "\tBob->>Alice: Great!"
        );
    }
}