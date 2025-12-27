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

package io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.model.bdd;

import io.techthinking.flowbdd.bdd.report.utils.Builder;
import org.springframework.http.HttpStatus;

public final class ThenGetBookByIsbnErrorBuilder implements Builder<ThenGetBookByIsbnError> {
    private HttpStatus statusCode;
    private String errorMessage;

    private ThenGetBookByIsbnErrorBuilder() {
    }

    public static ThenGetBookByIsbnErrorBuilder theErrorResponseCodeIs(HttpStatus statusCode) {
        return new ThenGetBookByIsbnErrorBuilder().withStatusCode(statusCode);
    }

    public static ThenGetBookByIsbnErrorBuilder theErrorResponseMessageIs(String errorMessage) {
        return new ThenGetBookByIsbnErrorBuilder().withErrorMessage(errorMessage);
    }

    protected ThenGetBookByIsbnErrorBuilder withStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    protected ThenGetBookByIsbnErrorBuilder withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ThenGetBookByIsbnError build() {
        return new ThenGetBookByIsbnError(statusCode, errorMessage);
    }
}
