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

package io.bitsmart.bdd.report.junit5.results.model.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextNotes {
    private final List<String> notes = new ArrayList<>();

    public List<String> getNotes() {
        return notes;
    }

    public void add(String note) {
        notes.add(note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextNotes)) return false;
        TextNotes textNotes1 = (TextNotes) o;
        return Objects.equals(notes, textNotes1.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes);
    }

    @Override
    public String toString() {
        return "TextNotes{" +
            "textNotes=" + notes +
            '}';
    }
}
