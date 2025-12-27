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

public final class NoteBuilder extends ExpressionBuilder<Note> {
    private String text;
    private String participant;
    private NoteLocationType position;

    private NoteBuilder() {
    }

    public static NoteBuilder aNote() {
        return new NoteBuilder();
    }

    public NoteBuilder text(String text) {
        this.text = text;
        return this;
    }

    public NoteBuilder participant(String participant) {
        this.participant = participant;
        return this;
    }

    public NoteBuilder position(NoteLocationType position) {
        this.position = position;
        return this;
    }

    @Override
    public Note build() {
        return new Note(text, participant, position);
    }
}
