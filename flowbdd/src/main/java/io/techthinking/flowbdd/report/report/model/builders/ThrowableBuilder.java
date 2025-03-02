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

package io.techthinking.flowbdd.report.report.model.builders;

import io.techthinking.flowbdd.report.report.model.Throwable;

import java.util.ArrayList;
import java.util.List;

public class ThrowableBuilder {
    private ClazzBuilder clazz;
    private String message;
    private ThrowableBuilder cause;
    private List<String> stackTrace = new ArrayList<>();

    private ThrowableBuilder() {
    }

    public static ThrowableBuilder aThrowable() {
        return new ThrowableBuilder();
    }

    public ThrowableBuilder withClazz(ClazzBuilder clazz) {
        this.clazz = clazz;
        return this;
    }

    public ThrowableBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ThrowableBuilder withCause(ThrowableBuilder cause) {
        this.cause = cause;
        return this;
    }

    public ThrowableBuilder withStacktrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    public Throwable build() {
        return new Throwable(
            clazz == null ? null : clazz.build(),
            message,
            cause == null ? null : cause.build(),
            stackTrace);
    }
}
