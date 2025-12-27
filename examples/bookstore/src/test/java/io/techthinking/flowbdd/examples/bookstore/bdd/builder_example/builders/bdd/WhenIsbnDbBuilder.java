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

package io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders.bdd;

import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.defaults.DefaultIsbnBook;
import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.model.bdd.WhenGetBookByIsbn;
import io.techthinking.flowbdd.bdd.report.utils.Builder;

public final class WhenIsbnDbBuilder implements Builder<WhenGetBookByIsbn> {
    private String isbn = DefaultIsbnBook.ISBN;

    private WhenIsbnDbBuilder() {
    }

    public static WhenIsbnDbBuilder aUserRequestsABook() {
        return new WhenIsbnDbBuilder();
    }

    public WhenIsbnDbBuilder withIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public WhenGetBookByIsbn build() {
        return new WhenGetBookByIsbn(isbn);
    }
}
