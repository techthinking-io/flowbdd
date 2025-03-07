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

package com.example.bookstore.bdd.builder_example.model.bdd;

import com.example.bookstore.bdd.builder_example.builders.IsbnBookBuilder;
import io.techthinking.flowbdd.bdd.report.utils.Builder;

public final class ThenGetBookByIsbnBuilder implements Builder<ThenGetBookByIsbn> {
    private IsbnBookBuilder book;

    private ThenGetBookByIsbnBuilder() {
    }

    public static ThenGetBookByIsbnBuilder theResponseContains(IsbnBookBuilder book) {
        return new ThenGetBookByIsbnBuilder().withBook(book);
    }

    protected ThenGetBookByIsbnBuilder withBook(IsbnBookBuilder book) {
        this.book = book;
        return this;
    }

    public ThenGetBookByIsbn build() {
        return new ThenGetBookByIsbn(book.build());
    }
}
