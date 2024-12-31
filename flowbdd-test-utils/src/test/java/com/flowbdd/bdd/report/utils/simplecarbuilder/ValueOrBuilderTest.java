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

package com.flowbdd.bdd.report.utils.simplecarbuilder;

import com.flowbdd.bdd.report.utils.Builder;
import com.flowbdd.bdd.report.utils.ValueOrBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValueOrBuilderTest {

    @Test
    void getStringValue() {
        ValueOrBuilder<String> string = new ValueOrBuilder<>("value");
        assertThat(string.get()).isEqualTo("value");
    }

    @Test
    void getStringValueFromBuilder() {
        ValueOrBuilder<String> string = new ValueOrBuilder<>(new StringBuilder());
        assertThat(string.get()).isEqualTo("built value");
    }

    @Test
    void handlesNullBuilder() {
        StringBuilder stringBuilder = null;
        ValueOrBuilder<String> string = new ValueOrBuilder<>(stringBuilder);
        assertThat(string.get()).isEqualTo(null);
    }

    @Test
    void handlesNullValue() {
        String stringValue = null;
        ValueOrBuilder<String> string = new ValueOrBuilder<>(stringValue);
        assertThat(string.get()).isEqualTo(null);
    }

    class StringBuilder implements Builder<String> {

        @Override
        public String build() {
            return "built value";
        }
    }
}

