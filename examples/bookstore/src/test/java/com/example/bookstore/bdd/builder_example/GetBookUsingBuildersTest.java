/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2022  James Bayliss
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

package com.example.bookstore.bdd.builder_example;

import com.example.bookstore.bdd.builder_example.factories.IsbnBookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.bookstore.bdd.builder_example.builders.IsbnBookBuilder.anIsbnBook;
import static com.example.bookstore.bdd.builder_example.builders.bdd.GivenIsbnDbBuilder.theIsbnDbContains;
import static com.example.bookstore.bdd.builder_example.builders.bdd.GivenIsbnDbEntryBuilder.forAnIsbn;
import static com.example.bookstore.bdd.builder_example.builders.bdd.WhenIsbnDbBuilder.aUserRequestsABook;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.INVALID_ISBN_LENGTH_1;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.INVALID_ISBN_LENGTH_11;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.INVALID_ISBN_LENGTH_12;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.INVALID_ISBN_LENGTH_14;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.INVALID_ISBN_LENGTH_9;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.AUTHOR;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.ISBN;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.ISBN_10_DIGITS;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.ISBN_13_DIGITS;
import static com.example.bookstore.bdd.builder_example.defaults.DefaultIsbnBook.TITLE;
import static com.example.bookstore.bdd.builder_example.factories.IsbnBookFactory.aDefaultIsbnBook;
import static com.example.bookstore.bdd.builder_example.model.bdd.ThenGetBookByIsbnBuilder.theResponseContains;
import static com.example.bookstore.bdd.builder_example.model.bdd.ThenGetBookByIsbnErrorBuilder.theErrorResponseCodeIs;
import static com.example.bookstore.bdd.builder_example.model.bdd.ThenGetBookByIsbnErrorBuilder.theErrorResponseMessageIs;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

public class GetBookUsingBuildersTest extends BaseBookStoreTest {

    @Override
    public void doc() {
        featureNotes("Working progress for example of usage Flow BDD");
    }

    @Test
    public void getBook() {
        when(aUserRequestsABook());
        then(theResponseContains(aDefaultIsbnBook()));
    }

    /**
     * Contrived example - but shows changing one value
     */
    @Test
    public void getBookUsingIsbn13() {
        given(theIsbnDbContains().anEntry(forAnIsbn(ISBN_13_DIGITS)
            .thatWillReturn(aDefaultIsbnBook().withIsbn(ISBN_13_DIGITS))));
        when(aUserRequestsABook().withIsbn(ISBN_13_DIGITS));
        then(theResponseContains(aDefaultIsbnBook().withIsbn(ISBN_13_DIGITS)));
    }

    @Test
    public void getBookUsingIsbn10() {
        given(theIsbnDbContains().anEntry(forAnIsbn(ISBN_13_DIGITS)
            .thatWillReturn(aDefaultIsbnBook().withIsbn(ISBN_13_DIGITS))));
        when(aUserRequestsABook().withIsbn(ISBN_10_DIGITS));
        then(theResponseContains(aDefaultIsbnBook().withIsbn(ISBN_13_DIGITS)));
    }

    /**
     * In this case - not a good use of a builder! AssertJ is better. What if we wanted theErrorResponseMessageContains?
     * BUT you need then() to capture for the UML diagram.
     * If the response was a unified object { status, errorMessage, results } maybe below abstraction is okay.
     */
    @ParameterizedTest(name = "#{index} - ISBN {0} not valid - returns warning")
    @ValueSource(strings = {
        INVALID_ISBN_LENGTH_1, INVALID_ISBN_LENGTH_9, INVALID_ISBN_LENGTH_11, INVALID_ISBN_LENGTH_12, INVALID_ISBN_LENGTH_14})
    public void getBookByIsbn_whenIsbnNumberIsNot10or13_return417StatusCode(String invalidIsbn) {
        when(aUserRequestsABook().withIsbn(invalidIsbn));
        then(theErrorResponseCodeIs(EXPECTATION_FAILED));
        then(theErrorResponseMessageIs("ISBN should be 10 or 13 digits. Spaces and dashes are allowed."));
    }

    /**
     * Similar to getBookUsingIsbn13 and getBookUsingIsbn10, but shows all values being set
     */
    @Test
    public void getBookByIsbnShowingAllDefaultValues() {
        given(theIsbnDbContains().anEntry(
            forAnIsbn(ISBN)
                .thatWillReturn(anIsbnBook()
                    .withIsbn(ISBN)
                    .withTitle(TITLE)
                    .withAuthor(AUTHOR))));
        when(aUserRequestsABook().withIsbn(ISBN));
        then(theResponseContains(anIsbnBook()
            .withIsbn(ISBN)
            .withTitle(TITLE)
            .withAuthors(AUTHOR)));
    }

//    /**
//     * TODO This is probably what you would do
//     * TODO read the test name - not the method name
//     */
//    @ParameterizedTest(name = "#{index} - ISBN {0} has valid length")
//    @ValueSource(strings = {isbn10, isbn13})
//    public void getBookAndValidateIsbnLength(String isbn) {
//        given(theIsbnDbContains().anEntry(
//            forAnIsbn(isbn)
//                .thatWillReturn(aDefaultIsbnBook()
//                    .withIsbn(isbn))));
//        when(aUserRequestsABook().withIsbn(isbn));
//        then(theResponseContains(aDefaultIsbnBook().withIsbn(isbn)));
//    }

}
