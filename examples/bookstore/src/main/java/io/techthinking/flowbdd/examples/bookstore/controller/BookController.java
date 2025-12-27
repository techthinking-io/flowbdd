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

package io.techthinking.flowbdd.examples.bookstore.controller;

import io.techthinking.flowbdd.examples.bookstore.service.BookIsbnService;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/**
 * <pre>
 * Additional endpoints for more testing.
 * - /book/isbn/{isbn} return IsbnBook from IsbnBookDB
 * - /book/search-vendors? return: BookSearchResultToBuy from OldBookSellerAPI and NewBookSellerAPI
 * - /book/search? return BookSearchResult
 * 	 - title = harry+potter
 * 	 - author = jk
 * 	 - isFistEdition = true
 * 	 - type = paper, hardback etc...
 * 	 - genre = action, adventure
 * 	 - condition = new, old
 * </pre>
 */
@RestController
public class BookController {
    private final BookIsbnService service;

    public BookController(BookIsbnService service) {
        this.service = service;
    }

    /**
     * Two standards for ISBN. ISBN 10 and ISBN 13
     * There is an algorithm to validate ISBN 10. Could be a downstream api call.
     *
     * Converting ResponseStatusException to ResponseEntity because message isn't working for ResponseStatusException.
     * //return new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Expectation Failed - ISBN should be 10 or 13 digits");
     */
    @GetMapping(value = "/book/{isbn}", produces = "application/json")
    public Object getBookByIsbn(@PathVariable String isbn) throws IOException {
        ISBNValidator validator = new ISBNValidator();
        if (!validator.isValid(isbn)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("ISBN should be 10 or 13 digits. Spaces and dashes are allowed.");
        }
        isbn = validator.validate(isbn);
        try {
            return service.get(isbn);
        } catch (ResponseStatusException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(e.getStatus()).body("No book found for ISBN: " + isbn);
            }
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
