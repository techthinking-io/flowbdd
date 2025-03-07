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

package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class IsbnBook {
    private final String isbn;
    private final String title;
    private final List<String> authors;

    @JsonCreator
    public IsbnBook(
        @JsonProperty("isbn") String isbn,
        @JsonProperty("title") String title,
        @JsonProperty("authors") List<String> authors) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IsbnBook)) return false;
        IsbnBook that = (IsbnBook) o;
        return Objects.equals(isbn, that.isbn) && Objects.equals(title, that.title) && Objects.equals(authors, that.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, authors);
    }

    @Override
    public String toString() {
        return "BookIsbnData{" +
            "isbn='" + isbn + '\'' +
            ", title='" + title + '\'' +
            ", authors=" + authors +
            '}';
    }
}
