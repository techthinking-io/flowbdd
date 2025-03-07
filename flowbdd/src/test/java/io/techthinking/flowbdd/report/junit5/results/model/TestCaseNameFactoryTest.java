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

package io.techthinking.flowbdd.report.junit5.results.model;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class TestCaseNameFactoryTest {

    private final TestCaseNameFactory testCaseNameFactory = new TestCaseNameFactory();

    @Test
    void createName_withEmptyArgs() {
        assertThat(testCaseNameFactory.createName("Method name", emptyList())).isEqualTo("Method name");
    }

    @Test
    void createName_withOneArgs() {
        assertThat(testCaseNameFactory.createName("Method name", singletonList("arg 1"))).isEqualTo("Method name arg 1");
    }

    @Test
    void createName_withTwoArgs() {
        assertThat(testCaseNameFactory.createName("Method name", asList("arg 1", "arg 2"))).isEqualTo("Method name arg 1, arg 2");
    }

    @Test
    void createName_withTwoIntegerArgs() {
        assertThat(testCaseNameFactory.createName("Method name", asList(1, 2))).isEqualTo("Method name 1, 2");
    }

    @Test
    void createName_withOneNullArg() {
        assertThat(testCaseNameFactory.createName("Method name", singletonList(null))).isEqualTo("Method name null");
    }
}