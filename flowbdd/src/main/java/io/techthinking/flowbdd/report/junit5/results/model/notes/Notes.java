/*
 * Flow BDD - The productive way to test.
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

package io.techthinking.flowbdd.report.junit5.results.model.notes;

import java.util.Objects;

/**
 * usage:
 * note().text.add("This is a note")
 * note().uml.add(...)
 */
public class Notes {
    private final TextNotes textNotes = new TextNotes();
    private final SequenceDiagramFactory diagramFactory = new SequenceDiagramFactory();

    // https://mermaid.js.org/syntax/stateDiagram.html
    // note().rawDiagram.add(...)
    //private final RawDiagramFactory rawDiagramFactory = new RawDiagramFactory();

    public TextNotes text() {
        return textNotes;
    }

    public SequenceDiagramFactory diagrams() {
        return diagramFactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notes)) return false;
        Notes notes = (Notes) o;
        return Objects.equals(textNotes, notes.textNotes) && Objects.equals(diagramFactory, notes.diagramFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textNotes, diagramFactory);
    }

    @Override
    public String toString() {
        return "Notes{" +
            "textNotes=" + textNotes +
            ", diagrams=" + diagramFactory +
            '}';
    }
}
