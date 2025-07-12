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

public class Participant implements Expression {

    private final String name;
    private final ParticipantType type;
    private final Integer position;

    public Participant(String name, Integer position) {
        this(name, ParticipantType.PARTICIPANT, position);
    }

    public Participant(String name, ParticipantType type, Integer position) {
        this.name = name;
        this.type = type;
        this.position = position;
    }

    public Integer position() {
        return position;
    }

    @Override
    public String generate() {
        return type() + " "+ name;
    }

    private String type() {
        return type == ParticipantType.PARTICIPANT ? "participant" : "actor";
    }
}