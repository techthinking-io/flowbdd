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

package io.bitsmart.bdd.report.report.model.builders;

import io.bitsmart.bdd.report.report.model.Status;
import io.bitsmart.bdd.report.report.model.TestCase;
import io.bitsmart.bdd.report.report.model.TestCaseTimings;
import io.bitsmart.bdd.report.report.model.notes.Notes;
import io.bitsmart.bdd.report.utils.Builder;

public final class TestCaseBuilder implements Builder<TestCase> {
    private String wordify;
    private Status status;
    private ThrowableBuilder cause;
    private MethodBuilder method;
    private ClazzBuilder clazz;
    private Notes notes;
    private TestCaseTimings timings;

    private TestCaseBuilder() {
    }

    public static TestCaseBuilder aTestCase() {
        return new TestCaseBuilder();
    }

    public TestCaseBuilder withWordify(String wordify) {
        this.wordify = wordify;
        return this;
    }

    public TestCaseBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public TestCaseBuilder withCause(ThrowableBuilder cause) {
        this.cause = cause;
        return this;
    }

    public TestCaseBuilder withMethod(MethodBuilder method) {
        this.method = method;
        return this;
    }

    public TestCaseBuilder withClazz(ClazzBuilder clazz) {
        this.clazz = clazz;
        return this;
    }

    public TestCaseBuilder withNotes(Notes notes) {
        this.notes = notes;
        return this;
    }

    public TestCaseBuilder withTimings(TestCaseTimings timings) {
        this.timings = timings;
        return this;
    }

    @Override
    public TestCase build() {
        return new TestCase(
            wordify,
            status,
            cause != null ? cause.build() : null,
            method != null ? method.build() : null,
            clazz != null ? clazz.build() : null,
            notes,
            timings);
    }
}
