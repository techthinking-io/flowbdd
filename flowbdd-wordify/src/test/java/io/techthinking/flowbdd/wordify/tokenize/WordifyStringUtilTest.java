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

package io.techthinking.flowbdd.wordify.tokenize;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class WordifyStringUtilTest {

    @Test
    void wordifyCharArray() {
        char[] chars = "doSomething".toCharArray();
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName(chars, 0, chars.length)).isEqualTo("do something");

        chars = "do_something".toCharArray();
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName(chars, 0, chars.length)).isEqualTo("do something");

        chars = "do_something_".toCharArray();
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName(chars, 0, chars.length)).isEqualTo("do something");

        chars = "_do_something_".toCharArray();
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName(chars, 0, chars.length)).isEqualTo("do something");
    }

    @Test
    void wordifyString() {
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("doSomething")).isEqualTo("do something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do_something")).isEqualTo("do something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do_something_")).isEqualTo("do something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("_do_something_")).isEqualTo("do something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("__do_something_")).isEqualTo("do something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("__do__something__")).isEqualTo("do something");
    }

    @Test
    void wordifyMoreThanOneUnderscoreInARow() {
        char[] chars = "__do__something__".toCharArray();
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName(chars, 0, chars.length)).isEqualTo("do something");
    }

    @Test
    void upperCaseFirstChar_emptyString() {
        assertThat(WordifyStringUtil.upperCaseFirstChar("")).isEqualTo("");
    }

    @Test
    void upperCaseFirstChar() {
        assertThat(WordifyStringUtil.upperCaseFirstChar("String")).isEqualTo("String");
        assertThat(WordifyStringUtil.upperCaseFirstChar("string")).isEqualTo("String");
    }

    /**
     * If we only have uppercase then we can assume that we have a const
     */
    @Test
    void wordifyAcronyms() {
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("CONST")).isEqualTo("CONST");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("CONST_99")).isEqualTo("CONST_99");
    }

    @Test
    void wordifyNamesWithNumbers() {
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("doSomething99")).isEqualTo("do something 99");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do_something99")).isEqualTo("do something 99");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do_something_99")).isEqualTo("do something 99");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("_do_something_99")).isEqualTo("do something 99");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do99something")).isEqualTo("do 99 something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do99Something")).isEqualTo("do 99 something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do99_Something")).isEqualTo("do 99 something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do99__Something")).isEqualTo("do 99 something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("do_99_something")).isEqualTo("do 99 something");
        assertThat(WordifyStringUtil.wordifyMethodOrFieldName("getBookByIsbn_whenIsbnNumberIsNot10or13_return417StatusCode")).isEqualTo("get book by isbn when isbn number is not 10 or 13 return 417 status code");
    }
}