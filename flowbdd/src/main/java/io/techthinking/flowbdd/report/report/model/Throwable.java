/*
 * Smart BDD - The smart way to do behavior-driven development.
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

package io.techthinking.flowbdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * TODO is this assertion failure
 * <pre>{@code
 * expected field
 * actual field
 * org.opentest4j.AssertionFailedError:
 * Expecting:
 *  <true>
 * to be equal to:
 *  <false>
 * but was not.
 * }</pre>
 */
public class Throwable {
    private final Clazz clazz;
    private final String message;
    private final Throwable cause;
    private final List<String> stackTrace; // TODO I think empty by default

    @JsonCreator
    public Throwable(
        @JsonProperty("class") Clazz clazz,
        @JsonProperty("message") String message,
        @JsonProperty("cause") Throwable cause,
        @JsonProperty("stackTrace") List<String> stackTrace) {
        this.clazz = clazz;
        this.message = message;
        this.cause = cause;
        this.stackTrace = stackTrace;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Throwable)) return false;
        Throwable throwable = (Throwable) o;
        return Objects.equals(clazz, throwable.clazz) && Objects.equals(message, throwable.message) && Objects.equals(cause, throwable.cause) && Objects.equals(stackTrace, throwable.stackTrace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, message, cause, stackTrace);
    }

    @Override
    public String toString() {
        return "Throwable{" +
            "clazz=" + clazz +
            ", message='" + message + '\'' +
            ", cause=" + cause +
            ", stackTrace=" + stackTrace +
            '}';
    }
}
