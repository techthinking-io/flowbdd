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

package io.bitsmart.bdd.report.report.model.builders;

import io.bitsmart.bdd.report.junit5.results.model.notes.Notes;
import io.bitsmart.bdd.report.utils.Builder;

/**
 * TODO should we create builders for things that don't have logic in the build()?
 *
 * Usage:
 * notes().addTextNote("This is a new note").build();
 * Vs
 * Notes notes = new Notes();
 * notes.text().add("This is a new note");
 */
public final class NotesBuilder implements Builder<Notes> {
    private final Notes notes = new Notes();

    private NotesBuilder() {
    }

    public static NotesBuilder notes() {
        return new NotesBuilder();
    }

    public NotesBuilder addTextNote(String note) {
        notes.text().add(note);
        return this;
    }

    public Notes build() {
        return notes;
    }
}
