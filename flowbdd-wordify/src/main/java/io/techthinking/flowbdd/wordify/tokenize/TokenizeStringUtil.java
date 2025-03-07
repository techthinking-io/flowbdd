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

public class TokenizeStringUtil {

    /**
     * Remove all new lines before code.
     * Remove all white space after code.
     */
    public static String stripJavaCode(String src) {
        int lastNewLineBeforeCode = 0;
        int lastWhiteSpaceAfterCode = src.length();
        for (int i = 0; i < src.length(); i++) {
            char ch = src.charAt(i);
            if (ch == TokenizeSource.NEW_LINE) {
                lastNewLineBeforeCode = i + 1;
            } else if (!Character.isWhitespace(ch)) {
                break;
            }
        }
        for (int i = src.length() - 1; i >= 0; i--) {
            char ch = src.charAt(i);
            if (Character.isWhitespace(ch)) {
                lastWhiteSpaceAfterCode = i;
            } else {
                break;
            }
        }

        if (lastNewLineBeforeCode >= lastWhiteSpaceAfterCode) {
            return "";
        }
        return src.substring(lastNewLineBeforeCode, lastWhiteSpaceAfterCode);
    }
}
