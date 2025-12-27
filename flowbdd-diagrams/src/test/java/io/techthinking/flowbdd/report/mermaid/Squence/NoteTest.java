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

import static org.assertj.core.api.Assertions.assertThat;

class NoteTest {

    @Test
    void generateNoteRight() {
        Note note = new Note("This is note text", "User", NoteLocationType.RIGHT);
        assertThat(note.generate()).isEqualTo("Note right of User: This is note text");
    }

    @Test
    void generateNoteLeft() {
        Note note = new Note("This is note text", "User", NoteLocationType.LEFT);
        assertThat(note.generate()).isEqualTo("Note left of User: This is note text");
    }

    @Test
    void generateNoteDefaultPosition() {
        Note note = new Note("This is note text", "User");
        assertThat(note.generate()).isEqualTo("Note right of User: This is note text");
    }
}