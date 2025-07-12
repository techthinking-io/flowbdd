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

import io.techthinking.flowbdd.report.mermaid.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final List<Participant> participants = new ArrayList<>();
    private final Map<String, Integer> participantsOrder = new HashMap<>();
    private final List<Expression> expressions = new ArrayList<>();

    @Override
    public String generate() {
        return "sequenceDiagram\n"
            + participants.stream().map(Expression::generate).collect(Collectors.joining("\n\t", "\t", "\n"))
            + expressions.stream().map(Expression::generate).collect(Collectors.joining("\n\t", "\t", ""));
    }

    public SequenceDiagram addActor(String name) {
        //TODO if "Actor" throw illegal state exception
        participants.add(new Participant(name, ParticipantType.ACTOR, participants.size()));
        participantsOrder.put(name, participants.size());
        return this;
    }

    public SequenceDiagram addParticipant(String name) {
        participants.add(new Participant(name, participants.size()));
        participantsOrder.put(name, participants.size());
        return this;
    }

//    public SequenceDiagram addMessage(Message message) {
//        expressions.add(message);
//        return this;
//    }
//
//    public SequenceDiagram addNote(Note note) {
//        expressions.add(note);
//        return this;
//    }

    public SequenceDiagram add(Expression... expressions) {
        this.expressions.addAll(Arrays.asList(expressions));
        return this;
    }

    // TODO what if we don't have any actors and or participants should we create?
    // first from being an actor, then participants
//    public SequenceDiagram add(MessageBuilder message) {
//        messages.add(message.build());
//        return this;
//    }

    public SequenceDiagram add(ExpressionBuilder<?>... expressions) {
        for (ExpressionBuilder<?> messageBuilder : expressions) {
            this.expressions.add(messageBuilder.build());
        }
        return this;
    }
}