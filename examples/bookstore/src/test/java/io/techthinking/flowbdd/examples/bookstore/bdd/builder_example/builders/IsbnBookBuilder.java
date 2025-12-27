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

package io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders;

import io.techthinking.flowbdd.examples.bookstore.model.IsbnBook;
import io.techthinking.flowbdd.bdd.report.utils.Builder;

import java.util.Arrays;
import java.util.List;

public final class IsbnBookBuilder implements Builder<IsbnBook> {
    private String Isbn;
    private String title;
    private List<String> authors;

    private IsbnBookBuilder() {
    }

    public static IsbnBookBuilder anIsbnBook() {
        return new IsbnBookBuilder();
    }

    public IsbnBookBuilder withIsbn(String Isbn) {
        this.Isbn = Isbn;
        return this;
    }

    public IsbnBookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public IsbnBookBuilder withAuthor(String author) {
        this.authors = Arrays.asList(author);
        return this;
    }

    public IsbnBookBuilder withAuthors(String... authors) {
        this.authors = Arrays.asList(authors);
        return this;
    }

    public IsbnBookBuilder withAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public IsbnBook build() {
        return new IsbnBook(Isbn, title, authors);
    }
}
