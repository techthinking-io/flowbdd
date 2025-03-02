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

package io.techthinking.flowbdd.wordify.tokenize;

public class WordifyStringUtil {

    private static boolean PRINT = false;

    public static String wordifyMethodOrFieldName(String input) {
        return wordifyMethodOrFieldName(input.toCharArray(), 0, input.length());
    }

    public static String wordifyMethodOrFieldName(char[] input, int from, int to) {
        StringBuilder stringBuilder = new StringBuilder();
        char ch;
        char chPrevious = input[from];

        StringBuilder allCapsStringBuilder = new StringBuilder();
        boolean allCapsAssertion = true;
        for (int i = from; i < to; i++) {
            if (Character.isLowerCase(input[i]))  {
                allCapsAssertion = false;
                break;
            }
            allCapsStringBuilder.append(input[i]);
        }
        if (allCapsAssertion) {
            __print(allCapsStringBuilder);
            return allCapsStringBuilder.toString();
        }

        // remove trailing underscores
        for (int i = to - 1; i >= 0; i--) {
            if (input[i] == '_') {
                to--;
            } else {
                break;
            }
        }

        for (int i = from; i < to; i++) {
            ch = input[i];

            // underscores get turned to spaces
            if (ch == '_') {
                if (i != to - 1) {
                    if (chPrevious != '_') {
                        stringBuilder.append(" ");
                    }
                }
            }

            // transition to digit
            else if (!Character.isDigit(chPrevious) && Character.isDigit(ch)) {
                if (chPrevious != '_') {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(ch);
            }

            // transition from digit to char
            else if (Character.isDigit(chPrevious) && !Character.isDigit(ch)) {
                if (chPrevious != '_') {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(Character.toLowerCase(ch));
            }

            else if (Character.isUpperCase(ch)) {
                if (chPrevious != '_') {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(Character.toLowerCase(ch));
            }

            else {
                stringBuilder.append(ch);
            }
            chPrevious = ch;
        }

        __print(stringBuilder);
        return stringBuilder.toString();
    }

    public static String upperCaseFirstChar(String input) {
        if (input.length() == 0) {
            return input;
        } else {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }

    static private void __print(Object obj) {
        if (PRINT) {
            System.out.println("wordifyMethodOrFieldName: " + obj);
        }
    }
}
