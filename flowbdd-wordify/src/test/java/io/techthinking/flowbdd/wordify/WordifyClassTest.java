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

package io.techthinking.flowbdd.wordify;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class WordifyClassTest {
    @Test
    void methodNoParams() {
        assertThat(true).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "value 1" })
    void methodWithParams(Object key) {
        passingAssertionWith(key);
    }

    private void passingAssertionWith(Object param) {
    }

    //TODO handle unhappy path
    @Disabled
    @Test
    void wordifyMethodNotFound() {
        String wordify = new WordifyClass().wordify(this.getClass(), "doesNotExistMethod");
    }

    @Test
    void wordifyTestMethod() {
        String wordify = new WordifyClass().wordify(this.getClass(), "methodNoParams");
        assertThat(wordify).isEqualTo("Assert that true is true");
    }

    @Test
    void wordifyMethodWithParams() {
        String wordify = new WordifyClass().wordify(this.getClass(), "methodWithParams", singletonList("value 1"));
        assertThat(wordify).isEqualTo("Passing assertion with value 1");
    }

    @Test
    void wordifyMethodWithNullParams() {
        String wordify = new WordifyClass().wordify(this.getClass(), "methodWithParams", singletonList(null));
        assertThat(wordify).isEqualTo("Passing assertion with null");
    }

    @Test
    void wordifyMethodWithListParams() {
        String wordify = new WordifyClass().wordify(this.getClass(), "methodWithParams", singletonList(emptyList()));
        assertThat(wordify).isEqualTo("Passing assertion with []");
    }
}