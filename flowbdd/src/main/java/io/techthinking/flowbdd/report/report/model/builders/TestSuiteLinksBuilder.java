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

package io.techthinking.flowbdd.report.report.model.builders;

import io.techthinking.flowbdd.report.report.model.TestSuiteLinks;
import io.techthinking.flowbdd.bdd.report.utils.Builder;
import io.techthinking.flowbdd.bdd.report.utils.BuilderUtils;

import java.util.List;

public final class TestSuiteLinksBuilder implements Builder<TestSuiteLinks> {
    private List<TestSuiteNameToFileBuilder> testSuites;

    private TestSuiteLinksBuilder() {
    }

    public static TestSuiteLinksBuilder aTestSuiteLinks() {
        return new TestSuiteLinksBuilder();
    }

    public TestSuiteLinksBuilder withTestSuites(List<TestSuiteNameToFileBuilder> testSuites) {
        this.testSuites = testSuites;
        return this;
    }

    @Override
    public TestSuiteLinks build() {
        return new TestSuiteLinks(BuilderUtils.build((testSuites)));
    }
}
