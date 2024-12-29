/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2024  James Bayliss
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

package com.flowbdd.wordify.legacy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Deprecated
public class WordifyStringTest {
    // 'a,b'      = A b
    // 'a, b'     = A b
    // 'a,  b'    = A b
    // 'a(b,c)'   = A b c
    // 'a(b, c)'  = A b c
    // 'a (b, c)' = A b c
    // 'a (b, c)' = A b c
    // 'a(b.c,d)' = A b c d
    // 'aB'       = A b
    // 'aBCD'     = A BCD
    // 'aBCDEe'   = A BCD ee

    @Test
    void handlesWhiteSpaceCommaAndParentheses() {
        assertThat(new WordifyString("a,b").wordify()).isEqualTo("A b");
        assertThat(new WordifyString(("a, b")).wordify()).isEqualTo("A b");
        assertThat(new WordifyString(("a,  b")).wordify()).isEqualTo("A b");

        assertThat(new WordifyString(("a(b,c)")).wordify()).isEqualTo("A b c");
        assertThat(new WordifyString(("a(b, c)")).wordify()).isEqualTo("A b c");

        assertThat(new WordifyString(("a( b, c )")).wordify()).isEqualTo("A b c");
        assertThat(new WordifyString(("a ( b, c )")).wordify()).isEqualTo("A b c");
        assertThat(new WordifyString(("a  (  b  ,  c  )")).wordify()).isEqualTo("A b c");

        assertThat(new WordifyString((" a ( b, c )")).wordify()).isEqualTo("A b c");
    }

    @Test
    void handlesPeriod() {
        assertThat(new WordifyString("a.b").wordify()).isEqualTo("A b");
        assertThat(new WordifyString(("a. b")).wordify()).isEqualTo("A b");
        assertThat(new WordifyString(("a.  b")).wordify()).isEqualTo("A b");

        assertThat(new WordifyString(("a(b.c)")).wordify()).isEqualTo("A b c");
        assertThat(new WordifyString(("a(b. c)")).wordify()).isEqualTo("A b c");

        assertThat(new WordifyString(("a( b. c )")).wordify()).isEqualTo("A b c");
        assertThat(new WordifyString(("a ( b. c )")).wordify()).isEqualTo("A b c");
        assertThat(new WordifyString(("a  (  b  .  c  )")).wordify()).isEqualTo("A b c");

        assertThat(new WordifyString((" a ( b. c )")).wordify()).isEqualTo("A b c");
    }

    @Test
    void handlesWords() {
        assertThat(new WordifyString("aB").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("aBcD").wordify()).isEqualTo("A bc d");
    }

    @Test
    void handlesSemiColon() {
        assertThat(new WordifyString("aB;").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("aBcD;").wordify()).isEqualTo("A bc d");
    }

    @Test
    void handlesNumbers() throws Exception {
        assertThat(new WordifyString("-13.37").wordify()).isEqualTo(("-13.37"));
        assertThat(new WordifyString("-0").wordify()).isEqualTo(("-0"));
        assertThat(new WordifyString("-1").wordify()).isEqualTo(("-1"));
        assertThat(new WordifyString("-1 ").wordify()).isEqualTo(("-1"));
        assertThat(new WordifyString(" -1 ").wordify()).isEqualTo(("-1"));
        assertThat(new WordifyString("(-1)").wordify()).isEqualTo(("-1"));
        assertThat(new WordifyString(" ( -1 ) ").wordify()).isEqualTo(("-1"));
    }

    /**
     * See java.lang.Character.isWhitespace
     */
    @Test
    void handlesWhiteSpaceAtStart() {
        assertThat(new WordifyString(" aB").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("\taB").wordify()).isEqualTo("A b");
    }

    @Test
    void handlesWhiteSpaceAtEnd() {
        assertThat(new WordifyString(" aB ").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("\taB\t").wordify()).isEqualTo("A b");
    }

    @Test
    void handlesNewLines() {
        assertThat(new WordifyString("\naB").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("\na\nb").wordify()).isEqualTo("A\nB");
        assertThat(new WordifyString("\n\na\nb").wordify()).isEqualTo("A\nB");
        assertThat(new WordifyString("\n\na\n\nb").wordify()).isEqualTo("A\nB");
        assertThat(new WordifyString("\n\na\n\nb\n").wordify()).isEqualTo("A\nB");

        assertThat(new WordifyString("\n aB").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("\n aB ").wordify()).isEqualTo("A b");
        assertThat(new WordifyString("\na\n b").wordify()).isEqualTo("A\nB");
    }

    //TODO should not be lowercase in quotes
    //TODO space should not be added before Z
    @Disabled
    @Test
    void handlesEdgeCases() {
        assertThat(new WordifyString("a(\"z\")").wordify()).isEqualTo("A \"z\"");
        assertThat(new WordifyString("a(\"Z\")").wordify()).isEqualTo("A \"Z\"");
    }

    //TODO remove white space at the end of a line
    @Disabled
    @Test
    void handlesWhiteSpacePotentialRequirement() {
        assertThat(new WordifyString("\na \nb").wordify()).isEqualTo("A\nB");
    }

    // Below are tests from yatspec -  added to make sure important features are missed

    @Test
    void insertsASingleSpacesBetweenCapitalsAndTrims() {
         assertThat(new WordifyString("replaceAWithB;").wordify()).isEqualTo(("Replace a with b"));
         assertThat(new WordifyString("doesNotBlow").wordify()).isEqualTo(("Does not blow"));
    }

    @Test
    void wordifyShouldNotRemoveLineBreaks() {
        assertThat(new WordifyString("F\nBar").wordify()).isEqualTo(("F\nBar"));
        assertThat(new WordifyString("Foo\nBar").wordify()).isEqualTo(("Foo\nBar"));
    }

    @Test
    void insertsSpaceOnFullStops() throws Exception {
        assertThat(new WordifyString("something.and(then.perhaps, something.else)").wordify())
            .isEqualTo(("Something and then perhaps something else"));
    }

    @Test
    void doesNotInsertSpaceOrRemoveDecimalPointForFloats() throws Exception {
        assertThat(new WordifyString("assertThat(sqrt(421.0704)).isEqualTo((20.520));").wordify())
            .isEqualTo(("Assert that sqrt 421.0704 is equal to 20.520"));
        assertThat(new WordifyString("13.37 66.6 3.33").wordify())
            .isEqualTo(("13.37 66.6 3.33"));
        assertThat(new WordifyString("someMethod(12.5,99, 22.22").wordify())
            .isEqualTo(("Some method 12.5 99 22.22"));
    }

    @Test
    void doesNotRemoveMinusSignForNumbers() throws Exception {
        assertThat(new WordifyString("-13.37").wordify()).isEqualTo(("-13.37"));
        assertThat(new WordifyString("-0").wordify()).isEqualTo(("-0"));
        assertThat(new WordifyString("-1").wordify()).isEqualTo(("-1"));
    }

    @Test
    void assertJStyleTests() throws Exception {
        assertThat(new WordifyString("assertThat(true).isEqualTo(true)").wordify()).isEqualTo(("Assert that true is equal to true"));
    }

    @Disabled("This doesn't work with the current parser")
    @Test
    void handlesConstants() throws Exception {
        assertThat(new WordifyString("UPPERCASE").wordify()).isEqualTo(("UPPERCASE"));
        assertThat(new WordifyString("assert(TRUE)").wordify()).isEqualTo(("Assert TRUE"));
    }
}
