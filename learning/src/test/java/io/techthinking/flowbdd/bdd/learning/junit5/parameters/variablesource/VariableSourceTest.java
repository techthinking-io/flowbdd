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

package io.techthinking.flowbdd.bdd.learning.junit5.parameters.variablesource;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * https://www.baeldung.com/parameterized-tests-junit-5
 * depends on testCompile 'org.junit.jupiter:junit-jupiter-params:5.3.1'
 */
public class VariableSourceTest {

    static Stream<Arguments> arguments = Stream.of(
        Arguments.of(null, true), // null strings should be considered blank
        Arguments.of("", true),
        Arguments.of("  ", true),
        Arguments.of("not blank", false)
    );

    @ParameterizedTest
    @VariableSource("arguments")
    void isBlank_ShouldReturnTrueForNullOrBlankStringsVariableSource(String input, boolean expected) {
        assertEquals(expected, isBlank(input));
        assertThat(isBlank(input)).isEqualTo(expected);
    }

    private static boolean isBlank(String value) {
        return isEmpty(value) || isEmpty(value.trim());
    }

    private static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }
}
