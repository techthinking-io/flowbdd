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

package io.techthinking.flowbdd.report.mermaid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>{@code
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
 * }</pre>
 */
public class SequenceDiagram implements Expression {
    private List<Participant> participants = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    @Override
    public String generate() {
        return "sequenceDiagram\n"
            + participants.stream().map(Expression::generate).collect(Collectors.joining("\n\t", "\t", "\n"))
            + messages.stream().map(Expression::generate).collect(Collectors.joining("\n\t", "\t", ""));
    }

    public SequenceDiagram addActor(String name) {
        //TODO if "Actor" throw illegal state exception
        participants.add(new Participant(name, ParticipantType.ACTOR));
        return this;
    }

    public SequenceDiagram addParticipant(String name) {
        participants.add(new Participant(name));
        return this;
    }

    public SequenceDiagram addMessage(Message message) {
        messages.add(message);
        return this;
    }

    // TODO what if we don't have any actors and or participants should we create?
    // first from being an actor, then participants
    public SequenceDiagram add(MessageBuilder message) {
        messages.add(message.build());
        return this;
    }
}
