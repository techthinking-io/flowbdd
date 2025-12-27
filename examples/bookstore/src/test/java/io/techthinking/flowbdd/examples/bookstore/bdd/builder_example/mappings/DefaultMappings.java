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

package io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.mappings;

import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.BaseBookStoreTest;
import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.defaults.DefaultIsbnBook;

import static io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders.IsbnBookBuilder.anIsbnBook;
import static io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders.bdd.GivenIsbnDbBuilder.theIsbnDbContains;
import static io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders.bdd.GivenIsbnDbEntryBuilder.forAnIsbn;

public class DefaultMappings {

    private final BaseBookStoreTest baseBookStoreTest;

    public DefaultMappings(BaseBookStoreTest baseBookStoreTest) {
        this.baseBookStoreTest = baseBookStoreTest;
    }

    public void initDefaultMappings() {
        baseBookStoreTest.given(theIsbnDbContains().anEntry(
            forAnIsbn(DefaultIsbnBook.ISBN)
                .thatWillReturn(anIsbnBook()
                    .withIsbn(DefaultIsbnBook.ISBN)
                    .withTitle(DefaultIsbnBook.TITLE)
                    .withAuthor(DefaultIsbnBook.AUTHOR))));
    }
}
