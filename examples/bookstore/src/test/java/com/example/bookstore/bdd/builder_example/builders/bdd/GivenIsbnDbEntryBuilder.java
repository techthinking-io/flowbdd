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

package com.example.bookstore.bdd.builder_example.builders.bdd;

import com.example.bookstore.bdd.builder_example.builders.IsbnBookBuilder;
import com.example.bookstore.bdd.builder_example.model.bdd.GivenIsbnDbEntry;
import com.flowbdd.bdd.report.utils.Builder;

public final class GivenIsbnDbEntryBuilder implements Builder<GivenIsbnDbEntry> {
    private String isbn;
    private IsbnBookBuilder book;

    private GivenIsbnDbEntryBuilder() {
    }

    public static GivenIsbnDbEntryBuilder forAnIsbn(String isbn) {
        return new GivenIsbnDbEntryBuilder().withIsbn(isbn);
    }

    protected GivenIsbnDbEntryBuilder withIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public GivenIsbnDbEntryBuilder thatWillReturn(IsbnBookBuilder book) {
        this.book = book;
        return this;
    }

    public GivenIsbnDbEntry build() {
        return new GivenIsbnDbEntry(isbn, book.build());
    }
}
