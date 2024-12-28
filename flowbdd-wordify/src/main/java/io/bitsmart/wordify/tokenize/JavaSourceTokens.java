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

package io.bitsmart.wordify.tokenize;

import java.util.List;
import java.util.Objects;

import static io.bitsmart.wordify.tokenize.TokenType.NEW_LINE;

public class JavaSourceTokens {
    private List<Token> tokens;
    private final int whiteSpace;

    public JavaSourceTokens(List<Token> tokens, int whiteSpace) {
        this.tokens = tokens;
        this.whiteSpace = whiteSpace;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int getWhiteSpace() {
        return whiteSpace;
    }

    public String asString() {
        StringBuilder stringBuilder = new StringBuilder();
        TokenType prevTokenType = null;

        for(Token token: tokens) {
            if (token.getType() != NEW_LINE && prevTokenType != null && prevTokenType != NEW_LINE) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(token.asString());
            prevTokenType = token.getType();
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JavaSourceTokens)) return false;
        JavaSourceTokens javaSourceTokens = (JavaSourceTokens) o;
        return whiteSpace == javaSourceTokens.whiteSpace && Objects.equals(tokens, javaSourceTokens.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokens, whiteSpace);
    }

    @Override
    public String toString() {
        return "Line{" +
            "tokens=" + tokens +
            ", whiteSpace=" + whiteSpace +
            '}';
    }
}
