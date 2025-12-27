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

import io.techthinking.flowbdd.report.mermaid.Expression;

public class Note implements Expression {

    // maybe need to add position??
    private final String text;

    private final String participant;

    private final NoteLocationType position;

    public Note(String text, String participant) {
        this(text, participant, NoteLocationType.RIGHT);
    }

    public Note(String text, String participant, NoteLocationType position) {
        this.text = text;
        this.participant = participant;
        this.position = position;
    }

    @Override
    public String generate() {
        return "Note " + position.getValue() + " of " + participant + ": " + text;
    }
}