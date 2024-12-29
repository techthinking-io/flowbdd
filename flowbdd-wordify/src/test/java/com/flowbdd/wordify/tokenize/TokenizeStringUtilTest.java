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

package com.flowbdd.wordify.tokenize;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class TokenizeStringUtilTest {

    @Test
    void noNewLinesToStrip() {
        assertThat(TokenizeStringUtil.stripJavaCode("source code")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode(" source code")).isEqualTo(" source code");
    }

    @Test
    void stripNewLinesAtTheStart() {
        assertThat(TokenizeStringUtil.stripJavaCode("\nsource code")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("\n source code")).isEqualTo(" source code");
        assertThat(TokenizeStringUtil.stripJavaCode(" \n source code")).isEqualTo(" source code");
        assertThat(TokenizeStringUtil.stripJavaCode("\n")).isEqualTo("");
        assertThat(TokenizeStringUtil.stripJavaCode("\n ")).isEqualTo("");
        assertThat(TokenizeStringUtil.stripJavaCode(" \n ")).isEqualTo("");
    }

    @Test
    void strip2NewLinesAtTheStart() {
        assertThat(TokenizeStringUtil.stripJavaCode("\n\nsource code")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("\n\n source code")).isEqualTo(" source code");
        assertThat(TokenizeStringUtil.stripJavaCode(" \n\n source code")).isEqualTo(" source code");
        assertThat(TokenizeStringUtil.stripJavaCode("\n\n")).isEqualTo("");
        assertThat(TokenizeStringUtil.stripJavaCode("\n\n ")).isEqualTo("");
        assertThat(TokenizeStringUtil.stripJavaCode(" \n\n ")).isEqualTo("");
    }

    @Test
    void stripNewLinesAtTheEnd() {
        assertThat(TokenizeStringUtil.stripJavaCode("source code\n")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("source code\n ")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("source code \n ")).isEqualTo("source code");
    }

    @Test
    void strip2NewLinesAtTheEnd() {
        assertThat(TokenizeStringUtil.stripJavaCode("source code\n\n")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("source code\n\n ")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("source code \n\n ")).isEqualTo("source code");
    }

    @Test
    void stripNewLinesAtTheStartAndEnd() {
        assertThat(TokenizeStringUtil.stripJavaCode("\nsource code\n")).isEqualTo("source code");
        assertThat(TokenizeStringUtil.stripJavaCode("\n source code\n ")).isEqualTo(" source code");
        assertThat(TokenizeStringUtil.stripJavaCode(" \n source code \n ")).isEqualTo(" source code");
    }
}