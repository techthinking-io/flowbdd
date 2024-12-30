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

package com.flowbdd.report.report.model.builders;

import com.flowbdd.report.report.model.TestSuiteNameToFile;
import io.flowbdd.bdd.report.utils.Builder;

public final class TestSuiteNameToFileBuilder implements Builder<TestSuiteNameToFile>  {
    private String name;
    private String file;

    private TestSuiteNameToFileBuilder() {
    }

    public static TestSuiteNameToFileBuilder aTestSuiteNameToFile() {
        return new TestSuiteNameToFileBuilder();
    }

    public TestSuiteNameToFileBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TestSuiteNameToFileBuilder withFile(String file) {
        this.file = file;
        return this;
    }

    public TestSuiteNameToFile build() {
        return new TestSuiteNameToFile(name, file);
    }
}
